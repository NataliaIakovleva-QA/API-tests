package ru.iakovleva.api.models;

public enum PetStatus {
    AVAILABLE("available", false),
    PENDING("pending", false),
    SOLD("sold", false),
    NULL("null", true);

    private final String code;
    private final boolean expectedZeroResult;

    PetStatus(String code, boolean expectedZeroResult) {
        this.code = code;
        this.expectedZeroResult = expectedZeroResult;
    }

       public String getCode() {
        return code;
    }

     public boolean isExpectedZeroResult() {
        return expectedZeroResult;
    }
}