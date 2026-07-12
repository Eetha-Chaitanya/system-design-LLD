package com.systemdesign.lld.domain.card;

public class Card {
    private final String cardNumber;
    private final String accountId;
    private final String bankId;
    private String pin;
    private final CardType cardType;

    public Card(String cardNumber, String accountId, String bankId, String pin, CardType cardType) {
        this.cardNumber = cardNumber;
        this.accountId = accountId;
        this.bankId = bankId;
        this.pin = pin;
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBankId() {
        return bankId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean matchesPin(String pin) {
        return this.pin.equals(pin);
    }
}
