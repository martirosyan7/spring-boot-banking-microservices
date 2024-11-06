package com.banking.card_account_service.utils.generator;

import com.banking.card_account_service.repository.AccountRepository;
import com.banking.card_account_service.repository.CardRepository;
import com.banking.card_account_service.utils.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public class NumberGenerator {
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private CurrencyType currencyType;
    private Random random = new Random();

    public NumberGenerator(CardRepository cardRepository,CurrencyType currencyType) {
        this.cardRepository = cardRepository;
        this.currencyType = currencyType;
    }

    public NumberGenerator(AccountRepository accountRepository, CurrencyType currencyType) {
        this.accountRepository = accountRepository;
        this.currencyType = currencyType;
    }

    private String generateRandomMiddleDigits() {
        return String.format("%09d", random.nextInt(1000000000));
    }

    public String generateCardNumber() {
        String issuerNumber = "400000";
        String cardNumber;
        do {
            String middleDigits = generateRandomMiddleDigits();
            int checkDigit = calculateCheckDigit(issuerNumber, middleDigits);

            cardNumber = String.format("%s%s%d", issuerNumber, middleDigits, checkDigit);
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    public String generateAccountNumber() {
        String accountIdentificationNumber = "1570";
        String accountNumber;
        do {
            String middleDigits = generateRandomMiddleDigits();
            int currencyDigits = currencyType.getCurrencyDigits();

            accountNumber = String.format("%s%s%d", accountIdentificationNumber, middleDigits, currencyDigits);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private int calculateCheckDigit(String issuerId, String middleDigits) {
        String cardNumber = issuerId + middleDigits;
        int sum = 0;
        boolean isOdd = true;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = cardNumber.charAt(i) - '0';

            if (isOdd) {
                sum += digit;
            } else {
                sum += digit * 2;
                if (digit >= 5) {
                    sum -= 9;
                }
            }

            isOdd = !isOdd;
        }

        return (sum * 9) % 10;
    }
}
