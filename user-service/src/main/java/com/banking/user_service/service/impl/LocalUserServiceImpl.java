package com.banking.user_service.service.impl;

import com.banking.user_service.model.LocalUser;
import com.banking.user_service.model.api.LoginBody;
import com.banking.user_service.model.dto.LocalUserDto;
import com.banking.user_service.model.dto.mapper.LocalUserMapper;
import com.banking.user_service.repository.LocalUserRepository;
import com.banking.user_service.service.LocalUserService;
import com.banking.user_service.service.encryption.EncryptionService;
import com.banking.user_service.service.jwt.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JWTService jwtService;


    @Override
    public LocalUserDto createUser(LocalUserDto userDto) {
        LocalUser user = LocalUserMapper.mapToUser(userDto);
        user.setPassword(encryptionService.encryptPassword(user.getPassword()));
        LocalUser savedUser = localUserRepository.save(user);
        return LocalUserMapper.mapToUserDto(savedUser);
    }

    @Override
    public LocalUserDto getUserById(UUID id) {
        LocalUser user = localUserRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        return LocalUserMapper.mapToUserDto(user);
    }

    @Override
    public List<LocalUserDto> getAllUsers() {
        Iterable<LocalUser> users = localUserRepository.findAll();
        List<LocalUser> userList = new ArrayList<>();
        users.forEach(userList::add);

        return userList.stream()
                .map((user) -> LocalUserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> user = localUserRepository.findByUsername(loginBody.getUsername());
        if (user.isPresent()) {
            LocalUser localUser = user.get();
            if (encryptionService.checkPassword(loginBody.getPassword(), localUser.getPassword())) {
                return jwtService.generateJWT(localUser);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void updateUserAccounts(UUID userId, UUID accountId) {
        LocalUser user = localUserRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        user.getAccounts().add(accountId);
        localUserRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserCards(UUID userId, UUID cardId) {
        LocalUser user = localUserRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        user.getCards().add(cardId);
        localUserRepository.save(user);
    }


}
