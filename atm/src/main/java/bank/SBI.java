package bank;

import domain.account.Account;
import domain.account.AccountFactory;
import domain.account.AccountType;
import domain.card.Card;
import domain.card.CardType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SBI implements Bank{
    private final String bankId;
    private final String bankName;

    private final Map<String, Account> accountsById;
    private final Map<String, Card> cardsById;

    public SBI(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
        accountsById = new HashMap<>();
        cardsById = new HashMap<>();
    }

    public String getBankId() {
        return bankId;
    }
    public String getBankName() {
        return bankName;
    }

    public Account openAccount(String accountId, String bankId, double balance, AccountType accountType){
        Account account = AccountFactory.createAccount(accountId, bankId, balance, accountType);
        accountsById.put(accountId, account);
        return account;
    }

    public Card issueCard(String cardNumber, String accountId, String pin, CardType cardType){
        if(!accountsById.containsKey(accountId)){
            throw new IllegalArgumentException("Cannot issue card as the Account "+accountId+" doesn't exist");
        }
        Card card = new Card(cardNumber, accountId, this.bankId, pin, cardType);
        cardsById.put(cardNumber, card);
        return card;
    }

    public boolean ownsCard(Card card){
        return cardsById.containsKey(card.getCardNumber());
    }

    public Account resolveAccount(Card card){
        return accountsById.get(card.getAccountId());
    }

    @Override
    public boolean authenticate(Card card, String pin) {
        return ownsCard(card) && card.matchesPin(pin);
    }

    @Override
    public double getAvailableBalance(Card card) {
        Account account = resolveAccount(card);
        if(account == null){
            throw new IllegalArgumentException("Invalid Card");
        }
        return account.getAvailableBalance();
    }

    @Override
    public boolean deposit(Card card, double amount) {
        Account account = resolveAccount(card);
        if(account == null){
            throw new IllegalArgumentException("Invalid Card");
        }
        account.credit(amount);
        return true;
    }

    @Override
    public boolean withdraw(Card card, double amount) {
        Account account = resolveAccount(card);
        if(account == null){
            throw new IllegalArgumentException("Invalid Card");
        }
        account.debit(amount);
        return true;
    }

    @Override
    public List<String> getMiniStatement(Card card) {
        Account account = resolveAccount(card);
        if(account == null){
            throw new IllegalArgumentException("Invalid Card");
        }
        return account.getMiniStatement();
    }
}
