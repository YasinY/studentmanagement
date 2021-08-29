package com.yasinyazici.studentmanagement.data.dto.student;

import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class StudentDto {

    private final String name;

    private final String address;

    private final UniversityEnrollment universityEnrollment;

}
