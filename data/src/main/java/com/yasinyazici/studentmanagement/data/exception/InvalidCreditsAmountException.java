package com.yasinyazici.studentmanagement.data.exception;

public class InvalidCreditsAmountException extends IllegalArgumentException {

    public InvalidCreditsAmountException(short credits) {
        super(String.format("Invalid amount of credits: %d", credits));
    }
}
