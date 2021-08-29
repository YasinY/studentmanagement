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

    public void createNewStudent(String universityName, String name, String studentAddress) {
        final boolean studentAlreadyExists = !studentRepository.findByName(name).isEmpty() && !studentRepository.findByAddress(studentAddress).isEmpty();
        if (studentAlreadyExists) {
            throw new ExistentStudentException(name);
        }
        final UniversityDao universityDao = retrieveUniversityDao(universityName);
        final UniversityEnrollment universityEnrollment = generateNewUniversityEnrollment(universityName);

        final StudentDao createdStudentDto = studentDaoFactory.create(name, studentAddress, universityEnrollment);
        studentRepository.save(createdStudentDto);

        universityDao.getStudentEnrollmentIds().add(createdStudentDto.getUniversityEnrollment().getEnrollmentId());

        universityRepository.save(universityDao);
        log.info("Created new student");
    }

    public void updateExistingStudent(String newUniversityName, String name, String address) {
        StudentDao student = getStudent(name, address);

        final UniversityEnrollment studentUniversityEnrollment = student.getUniversityEnrollment();

        final String currentStudentsUniversityName = studentUniversityEnrollment.getUniversityName();
        final UniversityDao currentUniversityDao = retrieveUniversityDao(currentStudentsUniversityName);
        final boolean universityChanged = !newUniversityName.equalsIgnoreCase(currentStudentsUniversityName);

        if (universityChanged) {
            handleUniversityChange(newUniversityName, student, currentUniversityDao);
        }
    }

    public List<StudentDto> getExistingStudents() {
        final List<StudentDto> studentDtos = studentRepository.findAll().stream().map(studentDaoToDtoMapper::toValue).collect(Collectors.toList());

        return studentDtos;
    }

    public void deleteStudent(String name, String address) {
        final StudentDao student = getStudent(name, address);
        final UniversityDao universityDao = retrieveUniversityDao(student.getUniversityEnrollment().getUniversityName());

        studentRepository.delete(student);
        universityDao.getStudentEnrollmentIds().removeIf(universityDaoId -> universityDaoId == student.getUniversityEnrollment().getEnrollmentId());
    }

    private StudentDao getStudent(String name, String address) {
        StudentDao student = studentRepository.findByNameAndAddress(name, address);
        if (student == null) { //todo usually optionals would be better, does mongo support that? idk no time
            throw new NotExistentStudentException(name, address);
        }

        return student;
    }

    private void handleUniversityChange(String newUniversityName, StudentDao student, UniversityDao currentUniversityDao) {

        currentUniversityDao.getStudentEnrollmentIds().remove(student.getUniversityEnrollment().getEnrollmentId());
        universityRepository.save(currentUniversityDao); // persist

        final UniversityDao newUniversityDao = retrieveUniversityDao(newUniversityName);

        final UniversityEnrollment newEnrollmentData = generateNewUniversityEnrollment(newUniversityName);

        final boolean studentAlreadyMatriculated = newUniversityDao.getStudentEnrollmentIds().contains(newEnrollmentData.getEnrollmentId());

        if (studentAlreadyMatriculated) {
            throw new StudentAlreadyMatriculatedException(student.getName(), newUniversityName);
        }

        student.setUniversityEnrollment(newEnrollmentData);
        studentRepository.save(student);
        newUniversityDao.getStudentEnrollmentIds().add(newEnrollmentData.getEnrollmentId());
        universityRepository.save(newUniversityDao);
    }
}
