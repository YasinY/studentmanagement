package com.yasinyazici.studentmanagement.data.factory;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;

public interface IStudentDaoFactory {

    StudentDao create(String name, String address, UniversityEnrollment universityEnrollment);
}
