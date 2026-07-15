package domain.transaction;

import bank.Bank;
import domain.card.Card;
import domain.transaction.strategy.BalanceInquiryTransaction;
import domain.transaction.strategy.DepositTransaction;
import domain.transaction.strategy.MiniStatementTransaction;
import domain.transaction.strategy.WithdrawTransaction;

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
