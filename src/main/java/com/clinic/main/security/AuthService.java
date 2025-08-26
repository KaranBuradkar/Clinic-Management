package com.clinic.main.security;

import com.clinic.main.dtos.LoginRequestDto;
import com.clinic.main.dtos.LoginResponseDto;
import com.clinic.main.dtos.SignupRequestDto;
import com.clinic.main.dtos.SignupResponseDto;
import com.clinic.main.entity.User;
import com.clinic.main.entity.type.RoleType;
import com.clinic.main.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final AuthTokenization authTokenization;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthTokenization authTokenization, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authTokenization = authTokenization;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername())
                .orElse(null);

        if (user != null) {
            log.error("User already exist.");
            throw new IllegalArgumentException("User already exist with username: "+signupRequestDto.getUsername());
        }

        user = userRepository.save(
                new User(
                        signupRequestDto.getUsername(),
                        passwordEncoder.encode(signupRequestDto.getPassword()),
                        signupRequestDto.getPhoneNo(), RoleType.PATIENT)
        );

        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        User user = (User) authentication.getPrincipal();
        String token = authTokenization.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }
}
