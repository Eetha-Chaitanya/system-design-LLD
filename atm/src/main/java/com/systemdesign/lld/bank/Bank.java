package com.systemdesign.lld.bank;

import com.systemdesign.lld.domain.card.Card;

import java.util.List;

public interface Bank {
    boolean authenticate(Card card, String pin);
    double getAvailableBalance(Card card);
    boolean deposit(Card card, double amount);
    boolean withdraw(Card card, double amount);
    List<String> getMiniStatement(Card card);
}
