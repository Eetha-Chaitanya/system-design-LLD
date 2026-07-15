package atm.state;

import atm.ATM;
import domain.card.Card;

public class IdleState implements ATMState {
    @Override
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        atm.setCurrentState(new HasCardState());
        System.out.println("Card has been inserted: "+card.getCardNumber()+" ("+card.getCardType()+")");
    }
}
