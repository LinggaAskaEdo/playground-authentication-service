package otis.playground.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otis.playground.authentication.model.Response;
import otis.playground.authentication.model.SuccessResponse;
import otis.playground.authentication.preference.ConstantPreference;
import otis.playground.common.secure.annotation.Secured;
import otis.playground.common.secure.model.SecureResponse;
import otis.playground.common.secure.model.enumeration.EGroup;
import otis.playground.common.secure.model.enumeration.EPermission;
import otis.playground.common.secure.model.enumeration.ERole;

@CrossOrigin(origins = "http://localhost:6060", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@Tag(name = "2. Test", description = "Collection test endpoint")
public class TestController {
    @Operation(summary = "Test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = ConstantPreference.AUTH_TEST)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SecureResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SecureResponse.class)) })
    })
    @Secured(groups = { EGroup.ALL }, roles = { ERole.ALL }, permissions = { EPermission.READ })
    @GetMapping("/secure")
    public ResponseEntity<Response> test(HttpServletRequest servletRequest, @RequestHeader(value="x-authenticated-user") String headerStr) {
        System.out.println("ID: " + servletRequest.getAttribute("id").toString());
        System.out.println("USERNAME: " + servletRequest.getAttribute("username").toString());

        SuccessResponse response = SuccessResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(ConstantPreference.RESPONSE_MESSAGE_OK)
                .description("YUHUUU !!!")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
