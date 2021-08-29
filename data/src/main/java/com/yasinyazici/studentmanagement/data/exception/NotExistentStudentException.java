package com.yasinyazici.studentmanagement.data.exception;

public class NotExistentStudentException extends IllegalStateException {

    public NotExistentStudentException(String name, String address) {
        super(String.format("Student with name %s and %s does not exist.", name, address));
    }
}
