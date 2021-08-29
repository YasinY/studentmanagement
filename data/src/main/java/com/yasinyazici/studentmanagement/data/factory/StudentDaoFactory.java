package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.exception.InvalidAddressException;
import com.yasinyazici.studentmanagement.data.exception.InvalidCreditsAmountException;
import com.yasinyazici.studentmanagement.data.exception.InvalidNameException;


public class StudentDaoFactory implements IStudentDaoFactory {



    @Override
    public StudentDao create(String name, String address, UniversityEnrollment universityEnrollment) {
        if(name.length() < FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED) {
            throw new InvalidNameException(name);
        }
        if(address.length() < FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED) {
            throw new InvalidAddressException(address);
        }

        StudentDao studentDao = StudentDao.builder()
                .name(name)
                .address(address)
                .universityEnrollment(universityEnrollment)
                .build();

        return studentDao;
    }
}
