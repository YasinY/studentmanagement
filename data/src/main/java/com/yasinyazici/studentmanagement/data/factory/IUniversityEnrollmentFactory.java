package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityEnrollmentState;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityProgress;

public interface IUniversityEnrollmentFactory {

    UniversityEnrollment createNew(String universityName);

    UniversityEnrollment createSpecific(String universityName, long enrollmentId, short credits, UniversityProgress universityProgress, UniversityEnrollmentState universityEnrollmentState);
}
