package com.systemdesign.lld;

import com.systemdesign.lld.bank.BankRouter;
import com.systemdesign.lld.bank.SBI;
import com.systemdesign.lld.domain.account.Account;
import com.systemdesign.lld.domain.account.AccountType;
import com.systemdesign.lld.atm.ATM;
import com.systemdesign.lld.domain.card.Card;
import com.systemdesign.lld.domain.card.CardType;
import com.systemdesign.lld.domain.transaction.TransactionType;

public class ATMDemo {
    public static void main(String[] args) {

        SBI sbi = new SBI("SBI-101", "SBI");

        // ONE customer, ONE savings account, TWO cards issued against it
        // (e.g. primary debit card + a supplementary/replacement debit card)
        Account savings = sbi.openAccount("SAV-1001", "SBI-101", 150000, AccountType.SAVINGS);
        Card primaryDebit = sbi.issueCard("SBI-D-1001", "SAV-1001", "1234", CardType.DEBIT);
        Card supplementaryDebit = sbi.issueCard("SBI-D-1002", "SAV-1001", "5678", CardType.DEBIT);

        // SAME customer also has a credit card -> a DIFFERENT account, different balance semantics
        Account credit = sbi.openAccount("CC-2001", "SBI-101",  200000, AccountType.CREDIT);
        Card creditCard = sbi.issueCard("SBI-C-9001", "CC-2001", "9999", CardType.CREDIT);

        BankRouter router = new BankRouter();
        router.registerBank("SBI-101", sbi);
        ATM atm = new ATM(router);

        System.out.println("===== Account SAV-1001 belongs to bank: " + savings.getBankId() + " =====");
        System.out.println("===== Account CC-2001 belongs to bank: " + credit.getBankId() + " =====");

        System.out.println("===== Scenario 1: Withdraw with primary debit card =====");
        atm.insertCard(primaryDebit);
        atm.enterPin("1234");
        atm.selectTransaction(TransactionType.WITHDRAW, 47000);
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY, 0);
        atm.ejectCard();

        System.out.println("\n===== Scenario 2: Same account, DIFFERENT card (supplementary) sees updated balance =====");
        atm.insertCard(supplementaryDebit);
        atm.enterPin("5678");
        atm.selectTransaction(TransactionType.WITHDRAW, 50000); // proves both cards share one account
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY, 0);
        atm.selectTransaction(TransactionType.DEPOSIT, 20000);
        atm.ejectCard();

        System.out.println("\n===== Scenario 3: Credit card cash advance (debit = increases debt) =====");
        atm.insertCard(creditCard);
        atm.enterPin("9999");
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY, 0); // available = limit - outstanding
        atm.selectTransaction(TransactionType.WITHDRAW, 50000);     // cash advance
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY, 0); // available should drop by 3000
        atm.ejectCard();

        System.out.println("\n===== Scenario 4: Credit card repayment (deposit = pays down debt) =====");
        atm.insertCard(creditCard);
        atm.enterPin("9999");
        atm.selectTransaction(TransactionType.DEPOSIT, 10000); // repayment
        atm.selectTransaction(TransactionType.MINI_STATEMENT, 0);
        atm.ejectCard();
    }
}
