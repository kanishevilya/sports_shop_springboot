package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.UserUpdate.*;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.service.UserQueriesService;
import com.example.SpringBootProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserQueriesService userQueriesService;

    @PutMapping("/updateEmail")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserEmail(@RequestBody UserUpdateEmailDTO dto) {
        userQueriesService.updateUserEmail(dto);
    }

    @PutMapping("/updateUsername")
    @ResponseStatus(HttpStatus.OK)
    public void updateUsername(@RequestBody UserUpdateUsernameDTO dto) {
        userQueriesService.updateUsername(dto);
    }

    @PutMapping("/updatePassword")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody UserUpdatePasswordDTO dto) {
        userQueriesService.updatePassword(dto);
    }

    @PutMapping("/updateFirstName")
    @ResponseStatus(HttpStatus.OK)
    public void updateFirstName(@RequestBody UserUpdateFirstNameDTO dto) {
        userQueriesService.updateFirstName(dto);
    }

    @PutMapping("/updateLastName")
    @ResponseStatus(HttpStatus.OK)
    public void updateLastName(@RequestBody UserUpdateLastNameDTO dto) {
        userQueriesService.updateLastName(dto);
    }

    @PutMapping("/updateCity")
    @ResponseStatus(HttpStatus.OK)
    public void updateCity(@RequestBody UserUpdateCityDTO dto) {
        userQueriesService.updateCity(dto);
    }

    @PutMapping("/updateCountry")
    @ResponseStatus(HttpStatus.OK)
    public void updateCountry(@RequestBody UserUpdateCountryDTO dto) {
        userQueriesService.updateCountry(dto);
    }

    @PutMapping("/updatePhone")
    @ResponseStatus(HttpStatus.OK)
    public void updatePhone(@RequestBody UserUpdatePhoneDTO dto) {
        userQueriesService.updatePhone(dto);
    }
}
