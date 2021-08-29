package com.yasinyazici.studentmanagement.controller;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StudentController {

    private final EnrollmentService enrollmentService;

    public StudentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody Map<String, String> body) {
        if(body == null) {
            return ResponseEntity.badRequest().build();
        }
        String universityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");
        if (hasMissingAttributesInBody(universityName, studentName, studentAddress)) {
            return ResponseEntity.badRequest().build();
        }
        if (hasInvalidBody(universityName, studentName, studentAddress)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.createNewStudent(universityName, studentName, studentAddress);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getStudents() {

        return ResponseEntity.ok(enrollmentService.getExistingStudents());
    }

    @PutMapping(value = "/student/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUniversity(@RequestBody Map<String, String> body) {
        if (body == null) {
            return ResponseEntity.badRequest().build();
        }
        String newUniversityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");

        if (hasMissingAttributesInBody(newUniversityName, studentName, studentAddress)) {
            return ResponseEntity.badRequest().build();
        }
        if (hasInvalidBody(newUniversityName, studentName, studentAddress)) {
            return ResponseEntity.badRequest().build();
        }


        enrollmentService.updateExistingStudent(studentName, studentAddress, newUniversityName);
        return ResponseEntity.ok().build();
    }

    private boolean hasMissingAttributesInBody(String universityName, String name, String address) {
        return universityName == null || name == null || address == null;
    }

    private boolean hasInvalidBody(String universityName, String name, String address) {
        return hasInvalidUniversityName(universityName)
                || hasInvalidStudentName(name)
                || hasInvalidAddress(address);
    }

    private boolean hasInvalidAddress(String address) {
        return address.length() < FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED;
    }

    private boolean hasInvalidStudentName(String name) {
        return name.length() < FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED;
    }

    private boolean hasInvalidUniversityName(String universityName) {
        return universityName.length() < FactoryConstants.MINIMUM_UNIVERSITY_NAME_LENGTH_REQUIREMENT;
    }
}
