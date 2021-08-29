package com.yasinyazici.studentmanagement.data.exception;

public class StudentAlreadyMatriculatedException extends IllegalStateException {

    public StudentAlreadyMatriculatedException(String name, String universityName) {
        super(String.format("Student %s already matriculated to university: %s ", name, universityName));
    }
}
