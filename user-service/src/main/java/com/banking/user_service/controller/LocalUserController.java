package com.banking.user_service.controller;


import com.banking.user_service.model.LocalUser;
import com.banking.user_service.model.api.LoginBody;
import com.banking.user_service.model.api.LoginResponse;
import com.banking.user_service.model.dto.LocalUserDto;
import com.banking.user_service.service.LocalUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class LocalUserController {

    @Autowired
    private LocalUserService localUserService;


    @PostMapping("/register")
    public ResponseEntity<LocalUserDto> createUser(@RequestParam String username, @RequestParam String firstName,
                                                   @RequestParam String lastName, @RequestParam String email,
                                                   @RequestParam String password, @RequestParam String address) {
        LocalUserDto userDto = new LocalUserDto();
        userDto.setUsername(username);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setAddress(address);

        LocalUserDto createdUser = localUserService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalUserDto> getUserById(@PathVariable UUID id) {
        LocalUserDto userDto = localUserService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<LocalUserDto>> getAllUsers() {
        List<LocalUserDto> users = localUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String token = localUserService.loginUser(loginBody);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            return ResponseEntity.ok(loginResponse);
        }
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public LocalUser getMe(@AuthenticationPrincipal LocalUser user) {
        return user;
    }

}
