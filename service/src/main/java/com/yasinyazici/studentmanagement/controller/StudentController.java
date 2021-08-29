package com.yasinyazici.studentmanagement.controller;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.service.EnrollmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StudentController {

    private final EnrollmentService enrollmentService;

    public StudentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody Map<String, String> body) {
        String universityName = body.get("universityName");
        String name = body.get("name");
        String address = body.get("address");
        if (hasMissingAttributesInBody(universityName, name, address)) {
            return ResponseEntity.badRequest().build();
        }
        if (hasInvalidBody(universityName, name, address)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.createNewStudent(universityName, name, address);
        return ResponseEntity.ok().build();
    }

    @GetMapping

    private boolean hasMissingAttributesInBody(String universityName, String name, String address) {
        return universityName == null || name == null || address == null;
    }

    private boolean hasInvalidBody(String universityName, String name, String address) {
        if (universityName.length() < FactoryConstants.MINIMUM_UNIVERSITY_NAME_LENGTH_REQUIREMENT
                || name.length() < FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED
                || address.length() < FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED) {
            return true;
        }
        return false;
    }
}
