package com.example.SpringBootProject.util.security;

import com.example.SpringBootProject.Exceptions.UserNotFoundException;
import com.example.SpringBootProject.model.CustomUserDetails;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UserNotFoundException("User not exist")
        );
        return new CustomUserDetails(user);
    }
}
