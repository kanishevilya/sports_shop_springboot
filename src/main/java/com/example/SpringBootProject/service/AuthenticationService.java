package com.example.SpringBootProject.service;

import com.example.SpringBootProject.Exceptions.CustomException;
import com.example.SpringBootProject.Exceptions.UserNotFoundException;
import com.example.SpringBootProject.dto.Authentication.LoginRequestDTO;
import com.example.SpringBootProject.dto.Authentication.RegisterRequestDTO;
import com.example.SpringBootProject.dto.Authentication.JwtTokenDTO;
import com.example.SpringBootProject.enums.ErrorCode;
import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.RoleRepository;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.util.security.JwtService;
import com.example.SpringBootProject.util.security.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final Integer timeOut=3;

    @Transactional
    public void registerUser(RegisterRequestDTO signRequestDto) {
        if (userRepository.findByUsername(signRequestDto.username()).isPresent()) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Username already taken");
        }
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = User.builder()
                .username(signRequestDto.username())
                .email(signRequestDto.email())
                .password(encoder.encode(signRequestDto.password()))
                .roles(roles)
                .build();
        userRepository.save(user);
    }


    public JwtTokenDTO fullAuthUserWithBan(LoginRequestDTO loginRequestDto){
        User user = userRepository.findByUsername(loginRequestDto.username()).orElseThrow(
                () -> new UserNotFoundException("Please register first")
        );
        LocalDateTime banStart=userRepository.getBanStartTimeByName(loginRequestDto.username());
        boolean passwordMatch = encoder.matches(loginRequestDto.password(), user.getPassword());
        if (!passwordMatch || (banStart != null && LocalDateTime.now().isBefore(banStart.plusMinutes(timeOut)))) {
            decrementAttempts(loginRequestDto, banStart);
        }
        if(userRepository.getAmountOfAttemptsByName(loginRequestDto.username())<3){
            userRepository.setAmountOfAttemptsByName(loginRequestDto.username(), 3);
            userRepository.setBanStartTimeByName(loginRequestDto.username(), null);
        }

        return authUser(loginRequestDto, user);
    }

    public void decrementAttempts(LoginRequestDTO loginRequestDto, LocalDateTime banStart){
        int amount;
        if(banStart!=null && LocalDateTime.now().isAfter(banStart.plusMinutes(timeOut))){
            amount=3;
            userRepository.setAmountOfAttemptsByName(loginRequestDto.username(), amount);
            userRepository.setBanStartTimeByName(loginRequestDto.username(), null);
        }else{
            amount=userRepository.getAmountOfAttemptsByName(loginRequestDto.username());
        }
        System.err.println(amount+" "+loginRequestDto.username());
        if(amount > 0){
            amount--;
            userRepository.setAmountOfAttemptsByName(loginRequestDto.username(), amount);
            int updatedAmount = userRepository.getAmountOfAttemptsByName(loginRequestDto.username());
            System.err.println("Updated amount of attempts: " + updatedAmount);
            throw new CustomException(ErrorCode.VALIDATION_ERROR ,
                    "Sorry but your password is wrong!!! attempts: "+updatedAmount);
        }else {
            if(amount==0){
                userRepository.setAmountOfAttemptsByName(loginRequestDto.username(), amount-1); // -1
                userRepository.setBanStartTimeByName(loginRequestDto.username(), LocalDateTime.now());
            }
            throw new CustomException(ErrorCode.VALIDATION_ERROR ,
                    "Sorry but the number of attempts is less than 0, time of unban: "+
                    userRepository.getBanStartTimeByName(loginRequestDto.username()).plusMinutes(timeOut));
        }
    }

    @Transactional
    public JwtTokenDTO authUser(LoginRequestDTO loginRequestDto, User user){
        if (!encoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR ,"Sorry but your password is wrong");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }
}
