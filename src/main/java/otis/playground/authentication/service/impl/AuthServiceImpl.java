package otis.playground.authentication.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otis.playground.authentication.exception.BadRequestException;
import otis.playground.authentication.exception.ResourceNotFoundException;
import otis.playground.authentication.exception.UnauthorizedException;
import otis.playground.authentication.model.Request;
import otis.playground.authentication.model.Response;
import otis.playground.authentication.model.RoleDTO;
import otis.playground.authentication.model.SuccessResponse;
import otis.playground.authentication.model.entity.Group;
import otis.playground.authentication.model.entity.Permission;
import otis.playground.authentication.model.entity.Role;
import otis.playground.authentication.model.entity.User;
import otis.playground.authentication.preference.ConstantPreference;
import otis.playground.authentication.repository.sql.GroupRepository;
import otis.playground.authentication.repository.sql.PermissionRepository;
import otis.playground.authentication.repository.sql.RoleRepository;
import otis.playground.authentication.repository.sql.UserRepository;
import otis.playground.authentication.service.AuthService;
import otis.playground.common.secure.model.UserGroup;
import otis.playground.common.secure.model.UserPrinciple;
import otis.playground.common.secure.model.UserRolePermission;
import otis.playground.common.secure.utils.JwtUtil;
import otis.playground.common.secure.utils.SecureUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final SecureUtil secureUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, GroupRepository groupRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder encoder, JwtUtil jwtUtil, SecureUtil secureUtil) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.secureUtil = secureUtil;
    }

    @Override
    public ResponseEntity<Response> authenticateUser(HttpServletRequest servletRequest, Request loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Username is not found"));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Wrong password");
        }

        if (!user.isActive()) {
            throw new UnauthorizedException("User is not active yet, please contact your administrator");
        }

        String ip = secureUtil.getClientIp(servletRequest);
        String agent = secureUtil.getUserAgent(servletRequest);

        UserPrinciple userPrinciple = new UserPrinciple();
        userPrinciple.setId(user.getId().toString());
        userPrinciple.setUsername(user.getUsername());

        List<UserRolePermission> userRolePermissions = new ArrayList<>();

        for (Role role : user.getRoles()) {
            UserRolePermission userRolePermission = new UserRolePermission();
            userRolePermission.setName(role.getName());
            userRolePermission.setPermissions(role.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()));

            userRolePermissions.add(userRolePermission);
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setName(user.getGroup().getName());
        userGroup.setRolePermissionSet(new HashSet<>(userRolePermissions));

        userPrinciple.setGroup(userGroup);

        String token = jwtUtil.generateToken(user.getUsername(), user.getId().toString(), ip, agent, userPrinciple);

        SuccessResponse response = SuccessResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(ConstantPreference.RESPONSE_MESSAGE_OK)
                .token(token)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Response> registerUser(Request signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), false);

        // set user group
        Group group = groupRepository.findByName(signUpRequest.getGroup().getName()).orElseThrow(() -> new ResourceNotFoundException("Role " + signUpRequest.getGroup().getName() + " is not found"));
        user.setGroup(group);

        Set<Role> roles = new HashSet<>();

        for (RoleDTO roleDTO : signUpRequest.getGroup().getRoles()) {
            Set<Permission> permissions = new HashSet<>();

            // set user role
            Role role = roleRepository.findByName(roleDTO.getName()).orElseThrow(() -> new ResourceNotFoundException("Role " + roleDTO.getName() + " is not found"));

            // set role permissions
            for (String aPerm : roleDTO.getPermissions()) {
                Permission readPermission = permissionRepository.findByName(aPerm).orElseThrow(() -> new ResourceNotFoundException("Permission " + aPerm + " is not found"));
                permissions.add(readPermission);
            }

            role.setPermissions(permissions);
            roleRepository.save(role);

            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);

        SuccessResponse response = SuccessResponse
                .builder()
                .status(HttpStatus.CREATED.value())
                .message(ConstantPreference.RESPONSE_MESSAGE_OK)
                .description("User registered successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
