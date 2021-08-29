package com.yasinyazici.studentmanagement.mapper;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.data.factory.IStudentDtoFactory;


public class StudentDaoToDtoMapper implements IMapper<StudentDao, StudentDto> {

    private final IStudentDtoFactory studentDtoFactory;

    public StudentDaoToDtoMapper(IStudentDtoFactory studentDtoFactory) {
        this.studentDtoFactory = studentDtoFactory;
    }

    @Override
    public StudentDao toKey(StudentDto value) {
        //not implemented
        return null;
    }

    @Override
    public StudentDto toValue(StudentDao studentDao) {
        final UniversityEnrollment universityEnrollment = studentDao.getUniversityEnrollment();
        final StudentDto studentDto = studentDtoFactory.create(
                studentDao.getName(),
                studentDao.getAddress(),
                universityEnrollment
        );

        return studentDto;
    }
}
