package com.systemdesign.lld.atm;

import com.systemdesign.lld.bank.Bank;
import com.systemdesign.lld.bank.BankRouter;
import com.systemdesign.lld.atm.state.ATMState;
import com.systemdesign.lld.atm.state.IdleState;
import com.systemdesign.lld.domain.card.Card;
import com.systemdesign.lld.domain.dispenser.CashDispenser;
import com.systemdesign.lld.domain.dispenser.chainofresponsibility.DispenseChain;
import com.systemdesign.lld.domain.dispenser.chainofresponsibility.NoteDispenser100;
import com.systemdesign.lld.domain.dispenser.chainofresponsibility.NoteDispenser200;
import com.systemdesign.lld.domain.dispenser.chainofresponsibility.NoteDispenser500;
import com.systemdesign.lld.domain.transaction.TransactionType;

public class ATM {
    private ATMState currentState = new IdleState();
    private Card currentCard;
    private Bank currentBank;
    private final CashDispenser cashDispenser;
    private final BankRouter bankRouter;

    public ATM(BankRouter bankRouter) {
        this.bankRouter = bankRouter;
        DispenseChain c1 = new NoteDispenser500(100);
        DispenseChain c2 = new NoteDispenser200(100);
        DispenseChain c3 = new NoteDispenser100(100);
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        cashDispenser = new CashDispenser(c1);
    }

    public void insertCard(Card card) {
        currentState.insertCard(this, card);
    }
    public void enterPin(String pin) {
        currentState.enterPin(this, pin);
    }
    public void selectTransaction(TransactionType transactionType, int amount) {
        currentState.selectTransaction(this, transactionType, amount);
    }
    public void ejectCard() {
        currentState.ejectCard(this);
    }

    public ATMState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ATMState state) {
        this.currentState = state;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card card) {
        this.currentCard = card;
    }

    public Bank getCurrentBank() {
        return currentBank;
    }

    public void setCurrentBank(Bank bank) {
        this.currentBank = bank;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public BankRouter getBankRouter() {
        return bankRouter;
    }
}
