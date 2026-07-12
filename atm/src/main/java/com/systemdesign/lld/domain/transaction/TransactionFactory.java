package com.systemdesign.lld.domain.transaction;

import com.systemdesign.lld.bank.Bank;
import com.systemdesign.lld.domain.card.Card;
import com.systemdesign.lld.domain.transaction.strategy.BalanceInquiryTransaction;
import com.systemdesign.lld.domain.transaction.strategy.DepositTransaction;
import com.systemdesign.lld.domain.transaction.strategy.MiniStatementTransaction;
import com.systemdesign.lld.domain.transaction.strategy.WithdrawTransaction;

public class TransactionFactory {
    public static Transaction createTransaction(Card card, Bank bank, int amount, TransactionType transactionType) {
        Transaction transaction = switch(transactionType){
            case DEPOSIT ->  new DepositTransaction();
            case WITHDRAW -> new WithdrawTransaction();
            case BALANCE_INQUIRY -> new BalanceInquiryTransaction();
            case MINI_STATEMENT -> new MiniStatementTransaction();
        };
        transaction.setCard(card);
        transaction.setBank(bank);
        transaction.setAmount(amount);
        return transaction;
    }
}
