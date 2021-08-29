package com.yasinyazici.studentmanagement.data.exception;

import java.util.NoSuchElementException;

public class NotExistentUniversityException extends NoSuchElementException {

    public NotExistentUniversityException(String universityName) {
        super(String.format("University with name: %s does not exist.", universityName));
    }
}
