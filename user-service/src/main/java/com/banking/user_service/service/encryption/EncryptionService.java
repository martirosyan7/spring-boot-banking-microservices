package com.banking.user_service.service.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final PasswordEncoder passwordEncoder;

    public EncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public String encryptPin(String pin) {
        return passwordEncoder.encode(pin);
    }

    public boolean checkPin(String pin, String hashedPin) {
        return passwordEncoder.matches(pin, hashedPin);
    }
}
