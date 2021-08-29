package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityEnrollmentState;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityProgress;
import com.yasinyazici.studentmanagement.data.exception.InvalidCreditsAmountException;
import com.yasinyazici.studentmanagement.data.exception.InvalidEnrollmentIdException;
import com.yasinyazici.studentmanagement.data.exception.InvalidUniversityNameException;

import java.util.concurrent.ThreadLocalRandom;

public class UniversityEnrollmentFactory implements IUniversityEnrollmentFactory {


    @Override
    public UniversityEnrollment createNew(String universityName) {
        if (universityName.length() < FactoryConstants.MINIMUM_UNIVERSITY_NAME_LENGTH_REQUIREMENT) {
            throw new InvalidUniversityNameException(universityName);
        }

        long enrollmentId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        final UniversityEnrollment newUniversityEnrollment = createSpecific(
                universityName,
                enrollmentId,
                (short) 0,
                UniversityProgress.NOT_FINISHED,
                UniversityEnrollmentState.MATRICULATED
        );

        return newUniversityEnrollment;
    }

    @Override
    public UniversityEnrollment createSpecific(String universityName, long enrollmentId, short credits, UniversityProgress universityProgress, UniversityEnrollmentState universityEnrollmentState) {
        if (enrollmentId < 0) {
            throw new InvalidEnrollmentIdException(enrollmentId);
        }
        if (credits < FactoryConstants.INVALID_CREDITS_TRIGGER) {
            throw new InvalidCreditsAmountException(credits);
        }

        final UniversityEnrollment universityEnrollment =
                UniversityEnrollment.builder()
                        .universityName(universityName)
                        .enrollmentId(enrollmentId)
                        .credits(credits)
                        .universityProgress(universityProgress)
                        .universityEnrollmentState(universityEnrollmentState)
                        .build();

        return universityEnrollment;
    }
}
