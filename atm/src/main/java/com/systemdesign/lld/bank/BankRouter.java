package com.systemdesign.lld.bank;

import com.systemdesign.lld.domain.card.Card;

import java.util.HashMap;
import java.util.Map;

public class BankRouter {
    private final Map<String, Bank> banks;
    public BankRouter() {
        banks = new HashMap<String, Bank>();
    }

    public void registerBank(String bankId, Bank bank) {
        banks.put(bankId, bank);
    }

    public Bank resolve(Card card) {
        Bank bank = banks.get(card.getBankId());
        if(bank == null) {
            throw new IllegalArgumentException("Bank with id " + card.getBankId() + " is not supported");
        }
        return bank;
    }
}
