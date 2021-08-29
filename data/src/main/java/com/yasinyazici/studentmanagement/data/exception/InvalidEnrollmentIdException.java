package com.yasinyazici.studentmanagement.data.exception;

public class InvalidEnrollmentIdException extends IllegalArgumentException {

    public InvalidEnrollmentIdException(long id) {
        super("Invalid enrollment id passed: " + id);
    }
}
