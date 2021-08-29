package com.yasinyazici.studentmanagement.data.dao.student;

import com.yasinyazici.studentmanagement.data.dao.university.UniversityEnrollmentState;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityProgress;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UniversityEnrollment {

    private final String universityName;

    private final long enrollmentId;

    private final short credits;

    private final UniversityProgress universityProgress;

    private final UniversityEnrollmentState universityEnrollmentState;

}
