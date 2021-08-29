package com.yasinyazici.studentmanagement.data.exception;

public class ExistentStudentException extends IllegalStateException {

    public ExistentStudentException(String name) {
        super(String.format("Student with name %s already exists.", name));
    }
}
