package com.systemdesign.lld.domain.account;

public class AccountFactory {
    public static Account createAccount(String accountId, String bankId, double balance, AccountType accountType){
        Account account;
        switch (accountType){
            case CREDIT :   account = new CreditAccount(accountId, bankId, balance);
                            break;
            default:        account = new SavingsAccount(accountId, bankId, balance);
        }
        return account;
    }
}
