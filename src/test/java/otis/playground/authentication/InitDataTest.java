package otis.playground.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import otis.playground.authentication.exception.ResourceNotFoundException;
import otis.playground.authentication.model.entity.Group;
import otis.playground.authentication.model.entity.Permission;
import otis.playground.authentication.model.entity.Role;
import otis.playground.authentication.model.entity.User;
import otis.playground.authentication.repository.sql.GroupRepository;
import otis.playground.authentication.repository.sql.PermissionRepository;
import otis.playground.authentication.repository.sql.RoleRepository;
import otis.playground.authentication.repository.sql.UserRepository;
import otis.playground.common.secure.model.enumeration.EGroup;
import otis.playground.common.secure.model.enumeration.EPermission;
import otis.playground.common.secure.model.enumeration.ERole;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDataTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    public void initiateGroup() {
        List<Group> groups = new ArrayList<>();

        groups.add(new Group(EGroup.IT.name()));
        groups.add(new Group(EGroup.MARKETING.name()));
        groups.add(new Group(EGroup.HR.name()));
        groups.add(new Group(EGroup.LEGAL.name()));
        groups.add(new Group(EGroup.SALES.name()));
        groups.add(new Group(EGroup.FINANCE.name()));

        groupRepository.saveAll(groups);
    }

    @Test
    public void initiateRole() {
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(ERole.CHIEF.name()));
        roles.add(new Role(ERole.VP.name()));
        roles.add(new Role(ERole.MANAGER.name()));
        roles.add(new Role(ERole.LEADER.name()));
        roles.add(new Role(ERole.MEMBER.name()));

        roleRepository.saveAll(roles);
    }

    @Test
    public void initiatePermission() {
        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission(EPermission.READ.name()));
        permissions.add(new Permission(EPermission.WRITE.name()));
        permissions.add(new Permission(EPermission.UPDATE.name()));
        permissions.add(new Permission(EPermission.DELETE.name()));

        permissionRepository.saveAll(permissions);
    }

    @Test
    public void initiateUser() {
        // Create new user's account
        User user = new User("linggaedo", encoder.encode("linggaedopass"), "lingga.edo@otis.com", true);

        // set user into group IT
        Group group = groupRepository.findByName(EGroup.IT.name()).orElseThrow(() -> new ResourceNotFoundException("Role user is not found"));
        user.setGroup(group);

        Set<Role> roles = new HashSet<>();

        Map<String, String[]> mapRoles = new HashMap<>();
        mapRoles.put(ERole.LEADER.name(), new String[] { EPermission.READ.name(), EPermission.WRITE.name(), EPermission.UPDATE.name(), EPermission.DELETE.name() });
        mapRoles.put(ERole.MEMBER.name(), new String[] { EPermission.READ.name() });

        for (Map.Entry<String, String[]> entry : mapRoles.entrySet()) {
            Set<Permission> permissions = new HashSet<>();

            Role role = roleRepository.findByName(entry.getKey()).orElseThrow(() -> new ResourceNotFoundException("Role admin is not found"));

            for (String aPerm : entry.getValue()) {
                Permission readPermission = permissionRepository.findByName(aPerm).orElseThrow(() -> new ResourceNotFoundException("Permission write is not found"));
                permissions.add(readPermission);
            }

            role.setPermissions(permissions);
            roleRepository.save(role);

            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}
