package com.yasinyazici.studentmanagement.service;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dao.student.UniversityEnrollment;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityDao;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityEnrollmentState;
import com.yasinyazici.studentmanagement.data.dao.university.UniversityProgress;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.data.exception.NotExistentUniversityException;
import com.yasinyazici.studentmanagement.data.exception.StudentAlreadyMatriculatedException;
import com.yasinyazici.studentmanagement.data.factory.IStudentDaoFactory;
import com.yasinyazici.studentmanagement.data.factory.IUniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.mapper.IMapper;
import com.yasinyazici.studentmanagement.repository.IStudentRepository;
import com.yasinyazici.studentmanagement.repository.IUniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private UniversityEnrollment generateUniversityEnrollmentByUniversityName(String universityName) {
        return universityEnrollmentFactory.createNew(universityName);
    }

    private UniversityDao retrieveUniversityDaoByUniversityName(String universityName) {
        final UniversityDao universityDao = universityRepository.findByUniversityName(universityName);
        if (universityDao == null) {
            throw new NotExistentUniversityException(universityName);
        }

        return universityDao;
    }

    private Optional<StudentDao> retrieveStudentByNameAndAddress(String studentName, String studentAddress) {
        StudentDao student = studentRepository.findByNameAndAddress(studentName, studentAddress);
        if (student == null) { //todo usually optionals would be better, does mongo support that? idk no time
            return Optional.empty();
        }

        return Optional.of(student);
    }


    private void handleUniversityChange(String newUniversityName, StudentDao studentDao, String currentStudentsUniversityName) {
        final long studentEnrollmentId = studentDao.getUniversityEnrollment().getEnrollmentId();

        removeFromUniversity(studentEnrollmentId, currentStudentsUniversityName);

        final UniversityDao newUniversityDao = retrieveUniversityDaoByUniversityName(newUniversityName);
        final UniversityEnrollment newEnrollmentData = generateUniversityEnrollmentByUniversityName(newUniversityName);
        final boolean studentAlreadyMatriculated = newUniversityDao.getStudentEnrollmentIds().contains(newEnrollmentData.getEnrollmentId());

        if (studentAlreadyMatriculated) {
            throw new StudentAlreadyMatriculatedException(studentDao.getName(), newUniversityName);
        }

        studentDao.setUniversityEnrollment(newEnrollmentData);
        studentRepository.save(studentDao);

        newUniversityDao.getStudentEnrollmentIds().add(newEnrollmentData.getEnrollmentId());
        universityRepository.save(newUniversityDao);
    }

    private void removeFromUniversity(long studentUniversityEnrollmentId, String currentStudentsUniversityName) {
        final UniversityDao currentUniversityDao = retrieveUniversityDaoByUniversityName(currentStudentsUniversityName);
        currentUniversityDao.getStudentEnrollmentIds().remove(studentUniversityEnrollmentId);
        universityRepository.save(currentUniversityDao); // persist
    }

    private UniversityEnrollment buildExmatriculateUniversityEnrollment(UniversityEnrollment universityEnrollment, boolean finished) {
        universityEnrollment.setUniversityEnrollmentState(UniversityEnrollmentState.EXMATRICULATED);
        universityEnrollment.setUniversityProgress(finished ? UniversityProgress.FINISHED : UniversityProgress.NOT_FINISHED);

        return universityEnrollment;
    }

    public void createNewStudent(String universityName, String studentName, String studentAddress) {
        final boolean studentAlreadyExists = !studentRepository.findByName(studentName).isEmpty() && !studentRepository.findByAddress(studentAddress).isEmpty();
        if (studentAlreadyExists) {
            return;
        }
        final UniversityDao universityDao = retrieveUniversityDaoByUniversityName(universityName);
        final UniversityEnrollment universityEnrollment = generateUniversityEnrollmentByUniversityName(universityName);

        final StudentDao createdStudentDto = studentDaoFactory.create(studentName, studentAddress, universityEnrollment);
        studentRepository.save(createdStudentDto);

        universityDao.getStudentEnrollmentIds().add(createdStudentDto.getUniversityEnrollment().getEnrollmentId());
        universityRepository.save(universityDao);
    }

    public void updateExistingStudent(String studentName, String studentAddress, String newUniversityName) {
        Optional<StudentDao> potentialStudent = retrieveStudentByNameAndAddress(studentName, studentAddress);

        potentialStudent.ifPresent(studentDao -> {
            final UniversityEnrollment studentUniversityEnrollment = studentDao.getUniversityEnrollment();
            final String currentStudentsUniversityName = studentUniversityEnrollment.getUniversityName();
            final boolean universityChanged = !newUniversityName.equalsIgnoreCase(currentStudentsUniversityName);

            if (universityChanged) {
                handleUniversityChange(newUniversityName, studentDao, currentStudentsUniversityName);
            }
        });
    }

    public List<StudentDto> getExistingStudentsByUniversityName(String university) {
        if (university.isEmpty()) {
            return studentRepository.findAll().stream().map(studentDaoToDtoMapper::toValue).collect(Collectors.toList());
        }

        // if scalability is a factor, we could use spring batch and implemement a processor which does exactly this
        return studentRepository.findAll()
                .stream()
                .map(studentDaoToDtoMapper::toValue)
                .filter(studentDto -> studentDto.getUniversityEnrollment().getUniversityName().equalsIgnoreCase(university))
                .collect(Collectors.toList());
    }

    public List<UniversityDao> getExistingUniversities() {
        return new ArrayList<>(universityRepository.findAll());
    }

    public void deleteStudent(String studentName, String studentAddress) {
        final Optional<StudentDao> potentialStudent = retrieveStudentByNameAndAddress(studentName, studentAddress);

        potentialStudent.ifPresent(student -> {
            final UniversityDao universityDao = retrieveUniversityDaoByUniversityName(student.getUniversityEnrollment().getUniversityName());
            studentRepository.delete(student);
            universityDao.getStudentEnrollmentIds().removeIf(universityDaoId -> universityDaoId == student.getUniversityEnrollment().getEnrollmentId());
        });
    }

    public void exmatriculateStudentFromCurrentUniversity(String studentName, String studentAddress, boolean finished) {
        final Optional<StudentDao> potentialStudent = retrieveStudentByNameAndAddress(studentName, studentAddress);

        potentialStudent.ifPresent(student -> {
            if(student.getUniversityEnrollment().getUniversityEnrollmentState() == UniversityEnrollmentState.EXMATRICULATED) {
                return;
            }
            final UniversityEnrollment universityEnrollment = buildExmatriculateUniversityEnrollment(student.getUniversityEnrollment(), finished);

            student.setUniversityEnrollment(universityEnrollment);
            studentRepository.save(student);
        });
    }
}
