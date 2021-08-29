package com.yasinyazici.studentmanagement.data.exception;

public class InvalidUniversityNameException extends IllegalArgumentException {

    public InvalidUniversityNameException(String universityName) {
        super(String.format("Invalid university name passed over: %s ", universityName));
    }
}
