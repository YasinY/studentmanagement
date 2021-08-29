package com.yasinyazici.studentmanagement.service;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityDao;
import com.yasinyazici.studentmanagement.data.exception.NotExistentUniversityException;
import com.yasinyazici.studentmanagement.data.exception.StudentAlreadyExistsException;
import com.yasinyazici.studentmanagement.data.factory.IStudentDaoFactory;
import com.yasinyazici.studentmanagement.data.factory.IUniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.data.factory.UniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.repository.IStudentRepository;
import com.yasinyazici.studentmanagement.repository.IUniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EnrollmentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IUniversityRepository universityRepository;

    private final IUniversityEnrollmentFactory universityEnrollmentFactory;

    private final IStudentDaoFactory studentDaoFactory;

    public EnrollmentService(IUniversityEnrollmentFactory universityEnrollmentFactory, IStudentDaoFactory studentDaoFactory) {
        this.universityEnrollmentFactory = universityEnrollmentFactory;
        this.studentDaoFactory = studentDaoFactory;
    }

    public void createNewStudent(String universityName, String name, String address) {
        if(!studentRepository.findByName(name).isEmpty() && !studentRepository.findByAddress(address).isEmpty()) {
            throw new StudentAlreadyExistsException(name);
        }
        final UniversityDao universityDao = universityRepository.findByUniversityName(universityName);
        if(universityDao == null) {
            throw new NotExistentUniversityException(universityName);
        }
        final UniversityEnrollment universityEnrollment = universityEnrollmentFactory.createNew(universityName);
        final StudentDao studentDao = studentDaoFactory.create(name, address, universityEnrollment);

        universityDao.getStudentEnrollmentIds().add(studentDao.getUniversityEnrollment().getEnrollmentId());
        studentRepository.save(studentDao);
        universityRepository.save(universityDao);
        log.info("Created new student");
    }
}
