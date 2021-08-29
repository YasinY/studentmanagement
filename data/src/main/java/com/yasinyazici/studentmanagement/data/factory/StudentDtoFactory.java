package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;

public class StudentDtoFactory implements IStudentDtoFactory {

    @Override
    public StudentDto create(String name, String address, UniversityEnrollment universityEnrollment) {
        //dont need to validate here as it comes from our DAO.
        final StudentDto studentDto = StudentDto.builder()
                .name(name)
                .universityEnrollment(universityEnrollment)
                .build();

        return studentDto;
    }
}
