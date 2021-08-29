package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.exception.InvalidAddressException;
import com.yasinyazici.studentmanagement.data.exception.InvalidNameException;


public class StudentDaoFactory implements IStudentDaoFactory {



    @Override
    public StudentDao create(String studentName, String studentAddress, UniversityEnrollment universityEnrollment) {
        if(studentName.length() < FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED) {
            throw new InvalidNameException(studentName);
        }
        if(studentAddress.length() < FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED) {
            throw new InvalidAddressException(studentAddress);
        }

        StudentDao studentDao = StudentDao.builder()
                .name(studentName)
                .address(studentAddress)
                .universityEnrollment(universityEnrollment)
                .build();

        return studentDao;
    }
}
