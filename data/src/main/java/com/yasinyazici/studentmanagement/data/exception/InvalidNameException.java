package com.yasinyazici.studentmanagement.data.exception;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;

public class InvalidNameException extends IllegalArgumentException {

    public InvalidNameException(String name) {
        super(String.format("Invalid name passed: %s must have at least %d characters", name, FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED));
    }
}
