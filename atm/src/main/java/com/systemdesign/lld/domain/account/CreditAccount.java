package com.systemdesign.lld.domain.account;

public class CreditAccount extends Account{
    private final double creditLimit;
    private double outstanding;

    public CreditAccount(String accountId, String bankId, double creditLimit) {
        super(accountId, bankId);
        this.creditLimit = creditLimit;
        this.outstanding = 0;
    }

    @Override
    public double getAvailableBalance() {
        return creditLimit - outstanding;
    }

    @Override
    public boolean debit(double amount) {
        if(amount > getAvailableBalance()){
            return false;
        }
        this.outstanding += amount;
        recordStatement(String.format("DEBIT: Rs %.2f | Outstanding: Rs %.2f | Available: Rs %.2f", amount, this.outstanding, getAvailableBalance()));
        return true;
    }

    @Override
    public boolean credit(double amount) {
        if(amount < 0){
            return false;
        }
        this.outstanding = Math.max(0, this.outstanding - amount);
        recordStatement(String.format("Repayment: Rs %.2f | Outstanding: Rs %.2f | Available: Rs %.2f", amount, this.outstanding, getAvailableBalance()));
        return true;
    }
}
