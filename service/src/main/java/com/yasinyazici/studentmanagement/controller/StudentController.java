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

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getStudents() {

        return ResponseEntity.ok(enrollmentService.getExistingStudents());
    }

    @PostMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody Map<String, String> body) {
        if(invalidBody(body) && !body.containsKey("universityName")) {
            return ResponseEntity.badRequest().build();
        }
        String universityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");
        if (hasInvalidBody(studentName, studentAddress, universityName)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.createNewStudent(universityName, studentName, studentAddress);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUniversity(@RequestBody Map<String, String> body) {
        if(invalidBody(body) && !body.containsKey("universityName")) {
            return ResponseEntity.badRequest().build();
        }
        String newUniversityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");

        if (hasInvalidBody(studentName, studentAddress, newUniversityName)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.updateExistingStudent(studentName, studentAddress, newUniversityName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudent(@RequestBody Map<String, String> body) {
        if(invalidBody(body)) {
            return ResponseEntity.badRequest().build();
        }

        String studentName = body.get("name");
        String studentAddress = body.get("address");

        if (hasInvalidStudentName(studentName) || hasInvalidAddress(studentAddress)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private boolean invalidBody(Map<String, String> body) {
        return body == null || !body.containsKey("name") || !body.containsKey("address");
    }

    private boolean hasInvalidBody(String name, String address, String universityName) {
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
