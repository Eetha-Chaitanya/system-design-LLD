package atm.state;

import atm.ATM;
import domain.card.Card;
import domain.transaction.TransactionType;

public interface ATMState {
    default void insertCard(ATM atm, Card card) {
        throw new IllegalStateException("Cannot insert card in this state: "+this.getClass().getSimpleName());
    }
    default void enterPin(ATM atm, String pin){
        throw new IllegalStateException("Cannot enter pin in this state: "+this.getClass().getSimpleName());
    }
    default void selectTransaction(ATM atm, TransactionType transactionType, int amount) {
        throw new IllegalStateException("Cannot select transaction in this state: "+this.getClass().getSimpleName());
    }
    default void ejectCard(ATM atm) {
        throw new IllegalStateException("Cannot eject card in this state: "+this.getClass().getSimpleName());
    }
}
