package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;

public interface IStudentDtoFactory {

    StudentDto create(String name, String address, UniversityEnrollment universityEnrollment);

}
