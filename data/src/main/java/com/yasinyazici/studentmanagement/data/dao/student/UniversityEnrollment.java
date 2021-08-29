package com.yasinyazici.studentmanagement.data.dao.student;

import com.yasinyazici.studentmanagement.data.dao.university.UniversityEnrollmentState;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityProgress;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UniversityEnrollment {

    private String universityName;

    private long enrollmentId;

    private short credits;

    private UniversityProgress universityProgress;

    private UniversityEnrollmentState universityEnrollmentState;

}
