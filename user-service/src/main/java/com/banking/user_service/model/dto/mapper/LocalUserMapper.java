package com.banking.user_service.model.dto.mapper;

import com.banking.user_service.model.LocalUser;
import com.banking.user_service.model.dto.LocalUserDto;

public class LocalUserMapper {

    public static LocalUser mapToUser(LocalUserDto userDto) {
        LocalUser user = new LocalUser(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getAddress(),
                userDto.getCards(),
                userDto.getAccounts()
        );
        return user;
    }

    public static LocalUserDto mapToUserDto(LocalUser user) {
        LocalUserDto userDto = new LocalUserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getCards(),
                user.getAccounts()
        );
        return userDto;
    }

}
