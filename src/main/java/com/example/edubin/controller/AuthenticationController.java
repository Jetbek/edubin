package com.example.edubin.controller;

import com.example.edubin.config.utils.GenerateToken;
import com.example.edubin.dto.request.UserLogin;
import com.example.edubin.dto.request.UserRegister;
import com.example.edubin.dto.response.ApiResponse;
import com.example.edubin.dto.response.TokenDTO;
import com.example.edubin.enitity.UserEntity;
import com.example.edubin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth/")
public final class AuthenticationController {

    private final UserService userService;
    private final GenerateToken generateToken;
    private final JwtAuthenticationProvider refreshTokenAuthProvider;

    public AuthenticationController(
            UserService userService,
            GenerateToken generateToken,
            @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider refreshTokenAuthProvider) {
        this.userService=userService;
        this.generateToken=generateToken;
        this.refreshTokenAuthProvider=refreshTokenAuthProvider;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<TokenDTO> registration(@Valid @RequestBody UserRegister userRegister) {

        TokenDTO token=userService.registerUser(userRegister);
        return new ApiResponse<>("registered successfully", token);
    }

    @GetMapping("/login")
    private ApiResponse<TokenDTO> login(@RequestBody UserLogin userLogin) {
        TokenDTO token = userService.login(userLogin);
        return new ApiResponse<>("login successfully",token);
    }
    @PostMapping("/token")
    public ResponseEntity<?> token(
            @RequestBody TokenDTO tokenDTO
    ) {
        Authentication authentication =
                refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
//        Jwt jwt = (Jwt) authentication.getCredentials();
//         check if present in db and not revoked, etc
        return ok(generateToken.createToken(authentication));
    }
}
