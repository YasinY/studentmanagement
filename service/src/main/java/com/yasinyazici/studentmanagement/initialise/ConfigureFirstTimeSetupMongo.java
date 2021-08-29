package com.yasinyazici.studentmanagement.initialise;

import com.yasinyazici.studentmanagement.data.dao.university.UniversityDao;
import com.yasinyazici.studentmanagement.repository.IStudentRepository;
import com.yasinyazici.studentmanagement.repository.IUniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@Configuration
public class ConfigureFirstTimeSetupMongo {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IUniversityRepository universityRepository;

    @PostConstruct
    public void initialise() {
        studentRepository.deleteAll();
        universityRepository.deleteAll();
        log.info("Cleared all repositories.");

        UniversityDao uniWienDao = UniversityDao.builder()
                .universityName("Universität Wien")
                .studentEnrollmentIds(Collections.emptyList())
                .build();

        UniversityDao uniHamburgDao = UniversityDao.builder()
                .universityName("Universität Hamburg")
                .studentEnrollmentIds(Collections.emptyList())
                .build();

        universityRepository.save(uniWienDao);
        universityRepository.save(uniHamburgDao);
    }

}
