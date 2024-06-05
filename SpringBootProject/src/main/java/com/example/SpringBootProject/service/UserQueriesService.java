package com.example.SpringBootProject.service;

import com.example.SpringBootProject.Exceptions.CustomException;
import com.example.SpringBootProject.dto.UserUpdate.*;
import com.example.SpringBootProject.enums.ErrorCode;
import com.example.SpringBootProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueriesService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void updateUserEmail(UserUpdateEmailDTO dto) throws CustomException {
        if(userRepository.updateEmail(dto.id(), dto.email()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "Email update error for id: "+dto.id());
        }
    }

    public void updateUsername(UserUpdateUsernameDTO dto) {
        if(userRepository.updateUsername(dto.id(), dto.username()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "Username update error for id: "+dto.id());
        }
    }

    public void updatePassword(UserUpdatePasswordDTO dto) {
        String encodedPassword = encoder.encode(dto.password());
        if(userRepository.updatePassword(dto.id(), encodedPassword) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "Password update error for id: "+dto.id());
        }
    }

    public void updateFirstName(UserUpdateFirstNameDTO dto) {
        if(userRepository.updateFirstName(dto.id(), dto.firstName()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "FirstName update error for id: "+dto.id());
        }
    }

    public void updateLastName(UserUpdateLastNameDTO dto) {
        if(userRepository.updateLastName(dto.id(), dto.lastName()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "LastName update error for id: "+dto.id());
        }
    }

    public void updateCity(UserUpdateCityDTO dto) {
        if(userRepository.updateCity(dto.id(), dto.city()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "City update error for id: "+dto.id());
        }
    }

    public void updateCountry(UserUpdateCountryDTO dto) {
        if(userRepository.updateCountry(dto.id(), dto.country()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "Country update error for id: "+dto.id());
        }
    }

    public void updatePhone(UserUpdatePhoneDTO dto) {
        if(userRepository.updatePhone(dto.id(), dto.phone()) == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "Phone update error for id: "+dto.id());
        }
    }
}
