package com.yasinyazici.studentmanagement.data.dto.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class StudentDto {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("universityEnrollment")
    private final UniversityEnrollment universityEnrollment;

}
