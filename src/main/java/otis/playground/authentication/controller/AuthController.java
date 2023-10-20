package otis.playground.authentication.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.JanLoebel.jsonschemavalidation.JsonSchemaValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otis.playground.authentication.model.ErrorResponse;
import otis.playground.authentication.model.Request;
import otis.playground.authentication.model.Response;
import otis.playground.authentication.model.Views;
import otis.playground.authentication.preference.ConstantPreference;
import otis.playground.authentication.service.AuthService;

@CrossOrigin(origins = "http://localhost:6060", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Authentication", description = "Collection auth endpoint")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = ConstantPreference.AUTH_LOGIN)) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/sign-in")
    public ResponseEntity<Response> authenticateUser(HttpServletRequest servletRequest, @RequestBody @JsonView(Views.SignIn.class) @JsonSchemaValidation("signin.json") Request loginRequest) {
        return authService.authenticateUser(servletRequest, loginRequest);
    }

    @Operation(summary = "Register Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = ConstantPreference.AUTH_REGISTER)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/sign-up")
    public ResponseEntity<Response> registerUser(@RequestBody @JsonView(Views.SignUp.class) @JsonSchemaValidation("signup.json") Request signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
