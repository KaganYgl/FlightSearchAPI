package com.kagan.flightapidemo.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Generate Authentication Token", description = "Generate an authentication token based on the provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }

}
