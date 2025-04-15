//package com.customgrant.custom_grant.services;
//
//import com.customgrant.custom_grant.repositories.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthorizationService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public AuthorizationService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(final String username) {
//        return this.userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("User not Found " + username));
//    }
//}
