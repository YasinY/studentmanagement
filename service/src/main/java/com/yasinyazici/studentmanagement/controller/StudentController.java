package com.yasinyazici.studentmanagement.controller;

import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.service.EnrollmentService;
import com.yasinyazici.studentmanagement.validation.BodyValidation;
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

    private final BodyValidation bodyValidation;

    public StudentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
        this.bodyValidation = new BodyValidation();
    }

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getStudents(@RequestParam(required = false) String university) {
        if(university != null && !university.isEmpty()) {
            return ResponseEntity.ok(enrollmentService.getExistingStudentsByUniversityName(university));
        }

        return ResponseEntity.ok(enrollmentService.getExistingStudentsByUniversityName(""));
    }

    @PostMapping(value = "/student")
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody Map<String, String> body) {
        if (bodyValidation.isEligibleStudentBody(body)) {
            return ResponseEntity.badRequest().build();
        }
        String universityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");
        if (bodyValidation.hasInvalidBody(studentName, studentAddress, universityName)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.createNewStudent(universityName, studentName, studentAddress);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/student")
    public ResponseEntity<?> updateUniversity(@RequestBody Map<String, String> body) {
        if (bodyValidation.isEligibleStudentBody(body)) {
            return ResponseEntity.badRequest().build();
        }
        String newUniversityName = body.get("universityName");
        String studentName = body.get("name");
        String studentAddress = body.get("address");

        if (bodyValidation.hasInvalidBody(studentName, studentAddress, newUniversityName)) {
            return ResponseEntity.badRequest().build();
        }

        enrollmentService.updateExistingStudent(studentName, studentAddress, newUniversityName);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/student")
    public ResponseEntity<?> deleteStudent(@RequestBody Map<String, String> body) {
        if (bodyValidation.invalidBody(body)) {
            return ResponseEntity.badRequest().build();
        }

        String studentName = body.get("name");
        String studentAddress = body.get("address");

        if (bodyValidation.hasInvalidStudentName(studentName) || bodyValidation.hasInvalidAddress(studentAddress)) {
            return ResponseEntity.badRequest().build();
        }
        enrollmentService.deleteStudent(studentName, studentAddress);

        return ResponseEntity.ok().build();
    }


    //TODO export to another controller
    @GetMapping(value = "/universities")
    public ResponseEntity<?> listAllUniversities() {

        return ResponseEntity.ok(enrollmentService.getExistingUniversities());
    }

    @PostMapping(value = "/university/exmatriculate")
    public ResponseEntity<?> exmatriculateStudentFromCurrentUniversity(@RequestBody Map<String, String> body) {
        if (bodyValidation.invalidBody(body) && !body.containsKey("finished")) {
            return ResponseEntity.badRequest().build();
        }
        String studentName = body.get("name");
        String studentAddress = body.get("address");
        boolean finished = Boolean.parseBoolean(body.get("finished"));

        if (bodyValidation.hasInvalidStudentName(studentName) || bodyValidation.hasInvalidAddress(studentAddress)) {
            return ResponseEntity.badRequest().build();
        }
        enrollmentService.exmatriculateStudentFromCurrentUniversity(studentName, studentAddress, finished);

        return ResponseEntity.ok().build();
    }
}
