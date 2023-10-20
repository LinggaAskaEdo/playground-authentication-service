package otis.playground.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import otis.playground.authentication.model.Request;
import otis.playground.authentication.model.Response;

public interface AuthService {
    ResponseEntity<Response> authenticateUser(HttpServletRequest servletRequest, Request loginRequest);
    ResponseEntity<Response> registerUser(Request signUpRequest);
}
