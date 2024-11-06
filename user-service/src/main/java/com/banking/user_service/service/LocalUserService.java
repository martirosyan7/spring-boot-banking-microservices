package com.banking.user_service.service;

import com.banking.user_service.model.api.LoginBody;
import com.banking.user_service.model.dto.LocalUserDto;

import java.util.List;
import java.util.UUID;

public interface LocalUserService {
    LocalUserDto createUser(LocalUserDto userDto);
    LocalUserDto getUserById(UUID id);
    List<LocalUserDto> getAllUsers();
    String loginUser(LoginBody loginBody);
    void updateUserAccounts(UUID userId, UUID accountId);
    void updateUserCards(UUID userId, UUID cardId);
}
