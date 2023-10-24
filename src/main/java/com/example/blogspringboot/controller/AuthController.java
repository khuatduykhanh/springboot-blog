package com.example.blogspringboot.controller;

import com.example.blogspringboot.dto.JWTAuthResponse;
import com.example.blogspringboot.dto.LoginDto;
import com.example.blogspringboot.dto.SigninDto;
import com.example.blogspringboot.entity.Role;
import com.example.blogspringboot.entity.User;
import com.example.blogspringboot.repository.RoleRepository;
import com.example.blogspringboot.repository.UserRepository;
import com.example.blogspringboot.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        return  ResponseEntity.ok(new JWTAuthResponse(token,refreshToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SigninDto signinDto){
        if(userRepository.existsByUsername(signinDto.getUsername())){
            return new ResponseEntity<>("username is already taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signinDto.getUsername())){
            return new ResponseEntity<>("email is already taken",HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signinDto.getName());
        user.setEmail(signinDto.getEmail());
        user.setUsername(signinDto.getUsername());
        user.setPassword(passwordEncoder.encode(signinDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        return new ResponseEntity<>("User rigistered successfully", HttpStatus.CREATED);
    }
////    @PostMapping("/refresh-token")
////    public void refreshToken(
////            HttpServletRequest request,
////            HttpServletResponse response
////    ) throws IOException {
////        service.refreshToken(request, response);
////    }


}
