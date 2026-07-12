package com.systemdesign.lld.domain.transaction;

public class TransactionResult {
    private boolean success;
    private String message;

    public TransactionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public static TransactionResult success(String message) {
        return new TransactionResult(true, message);
    }
    public static TransactionResult failure(String message) {
        return new TransactionResult(false, message);
    }
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        String result =  success ? "SUCCESS " : "FAILURE ";
        result += message;
        return result;
    }
}
