package com.yasinyazici.studentmanagement.repository;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IStudentRepository extends MongoRepository<StudentDao, String> {

    public List<StudentDao> findByName(String name);

    public List<StudentDao> findByAddress(String address);

    public StudentDao findByNameAndAddress(String name, String address);

    public List<StudentDao> findByUniversityEnrollment(UniversityEnrollment universityEnrollment);




}
