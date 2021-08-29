package com.yasinyazici.studentmanagement.data.exception;

public class StudentAlreadyExistsException extends IllegalStateException {

    public StudentAlreadyExistsException(String name) {
        super(String.format("Student with name %s already exists.", name));
    }
}
