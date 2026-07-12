package com.systemdesign.lld.domain.transaction;

import com.systemdesign.lld.bank.Bank;
import com.systemdesign.lld.domain.account.Account;
import com.systemdesign.lld.domain.card.Card;
import com.systemdesign.lld.domain.dispenser.CashDispenser;

public abstract class Transaction {
    protected Card card;
    protected Bank bank;
    protected int amount;
    public abstract TransactionResult execute(CashDispenser cashDispenser);

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
