package com.systemdesign.lld.domain.transaction.strategy;

import com.systemdesign.lld.domain.dispenser.CashDispenser;
import com.systemdesign.lld.domain.transaction.Transaction;
import com.systemdesign.lld.domain.transaction.TransactionResult;

public class WithdrawTransaction extends Transaction {
    @Override
    public TransactionResult execute(CashDispenser cashDispenser) {
        if(amount < 0){
            return TransactionResult.failure("Amount can not be negative");
        }
        double availableBalance = bank.getAvailableBalance(card);
        if(availableBalance < amount) {
            return TransactionResult.failure("Insufficient funds, available balance is: " + availableBalance);
        }
        boolean canDispense = cashDispenser.canDispense(amount);
        if(!canDispense){
            return TransactionResult.failure("Insufficient funds, ATM can not dispense the requested amount");
        }
        cashDispenser.dispense(amount);
        boolean debited = bank.withdraw(card, amount);
        if(!debited){
            //implement rollback mechanism in future
            //cashDispenser.rollback();
            return TransactionResult.failure("Bank declined the debit. Cash returned to inventory");
        }
        return TransactionResult.success("Please collect your cash");
    }
}
