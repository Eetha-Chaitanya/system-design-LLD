package com.systemdesign.lld.atm.state;

import com.systemdesign.lld.atm.ATM;
import com.systemdesign.lld.domain.card.Card;

public class IdleState implements ATMState {
    @Override
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        atm.setCurrentState(new HasCardState());
        System.out.println("Card has been inserted: "+card.getCardNumber()+" ("+card.getCardType()+")");
    }
}
