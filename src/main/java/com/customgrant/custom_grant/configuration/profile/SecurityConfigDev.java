package com.customgrant.custom_grant.configuration.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Profile("dev")
@Configuration
public class SecurityConfigDev {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfigDev(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("default")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
