package com.yasinyazici.studentmanagement.data.exception;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;

public class InvalidAddressException extends IllegalArgumentException {

    public InvalidAddressException(String address) {
        super(String.format("Invalid address passed: %s must have at least %d characters", address, FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED));
    }
}
