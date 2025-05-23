package com.customgrant.custom_grant.controllers;

import com.customgrant.custom_grant.configuration.TokenConfiguration;
import com.customgrant.custom_grant.dtos.AccessLoginDTO;
import com.customgrant.custom_grant.dtos.RegisterDTO;
import com.customgrant.custom_grant.entities.Role;
import com.customgrant.custom_grant.entities.User;
import com.customgrant.custom_grant.repositories.RoleRepository;
import com.customgrant.custom_grant.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenConfiguration tokenConfiguration;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final KafkaTemplate<String, AccessLoginDTO> kafkaTemplate;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenConfiguration tokenConfiguration, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, KafkaTemplate<String, AccessLoginDTO> kafkaTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenConfiguration = tokenConfiguration;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final RegisterDTO dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        final String encodePassword = passwordEncoder.encode(dto.password());
        final Role authority = roleRepository.findByAuthority(dto.role().toRoleType())
                .orElseThrow();

        User user = User.from(dto.username(), encodePassword, authority);
        user = userRepository.save(user);

        return ResponseEntity.ok(RegisterDTO.from(user));
    }
}
