package com.yasinyazici.studentmanagement.service;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityDao;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.data.exception.ExistentStudentException;
import com.yasinyazici.studentmanagement.data.exception.NotExistentStudentException;
import com.yasinyazici.studentmanagement.data.exception.NotExistentUniversityException;
import com.yasinyazici.studentmanagement.data.exception.StudentAlreadyMatriculatedException;
import com.yasinyazici.studentmanagement.data.factory.IStudentDaoFactory;
import com.yasinyazici.studentmanagement.data.factory.IUniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.mapper.IMapper;
import com.yasinyazici.studentmanagement.repository.IStudentRepository;
import com.yasinyazici.studentmanagement.repository.IUniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EnrollmentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IUniversityRepository universityRepository;

    @Autowired
    private IMapper<StudentDao, StudentDto> studentDaoToDtoMapper;

    private final IUniversityEnrollmentFactory universityEnrollmentFactory;

    private final IStudentDaoFactory studentDaoFactory;

    public EnrollmentService(IUniversityEnrollmentFactory universityEnrollmentFactory,
                             IStudentDaoFactory studentDaoFactory) {
        this.universityEnrollmentFactory = universityEnrollmentFactory;
        this.studentDaoFactory = studentDaoFactory;
    }

    private UniversityEnrollment generateNewUniversityEnrollment(String universityName) {
        final UniversityEnrollment universityEnrollment = universityEnrollmentFactory.createNew(universityName);

        return universityEnrollment;
    }

    private UniversityDao retrieveUniversityDao(String universityName) {
        final UniversityDao universityDao = universityRepository.findByUniversityName(universityName);
        if (universityDao == null) {
            throw new NotExistentUniversityException(universityName);
        }
        return universityDao;
    }

    public void createNewStudent(String universityName, String name, String address) {
        if (!studentRepository.findByName(name).isEmpty() && !studentRepository.findByAddress(address).isEmpty()) {
            throw new ExistentStudentException(name);
        }
        final UniversityDao universityDao = retrieveUniversityDao(universityName);
        final UniversityEnrollment universityEnrollment = generateNewUniversityEnrollment(universityName);
        final StudentDao studentDao = studentDaoFactory.create(name, address, universityEnrollment);

        universityDao.getStudentEnrollmentIds().add(studentDao.getUniversityEnrollment().getEnrollmentId());
        studentRepository.save(studentDao);
        universityRepository.save(universityDao);
        log.info("Created new student");
    }

    public List<StudentDto> getExistingStudents() {
        final List<StudentDto> studentDtos = studentRepository.findAll().stream().map(studentDaoToDtoMapper::toValue).collect(Collectors.toList());

        return studentDtos;
    }

    public void updateExistingStudent(String name, String address, UniversityEnrollment newEnrollmentData) {
        StudentDao student = studentRepository.findByNameAndAddress(name, address);
        if (student == null) { //todo usually optionals would be better, does mongo support that? idk no time
            throw new NotExistentStudentException(name, address);
        }

        final UniversityEnrollment studentUniversityEnrollment = student.getUniversityEnrollment();

        final String currentStudentsUniversityName = studentUniversityEnrollment.getUniversityName();
        final UniversityDao currentUniversityDao = retrieveUniversityDao(currentStudentsUniversityName);
        final boolean universityChanged = !newEnrollmentData.getUniversityName().equalsIgnoreCase(currentStudentsUniversityName);

        if (universityChanged) {
            handleUniversityChange(newEnrollmentData, student, currentUniversityDao);
        }

        student.setUniversityEnrollment(newEnrollmentData);
        studentRepository.save(student);
    }

    private void handleUniversityChange(UniversityEnrollment newEnrollmentData, StudentDao student, UniversityDao currentUniversityDao) {

        currentUniversityDao.getStudentEnrollmentIds().remove(student.getUniversityEnrollment().getEnrollmentId());
        universityRepository.save(currentUniversityDao); // persist

        final UniversityDao newUniversityDao = retrieveUniversityDao(newEnrollmentData.getUniversityName());

        final boolean studentAlreadyMatriculated = newUniversityDao.getStudentEnrollmentIds().contains(newEnrollmentData.getEnrollmentId());
        if (studentAlreadyMatriculated) {
            throw new StudentAlreadyMatriculatedException(student.getName(), newEnrollmentData.getUniversityName());
        }

        newUniversityDao.getStudentEnrollmentIds().add(newEnrollmentData.getEnrollmentId());
        universityRepository.save(newUniversityDao);
    }

    public void removeStudent(String name, String address) {

    }
}
