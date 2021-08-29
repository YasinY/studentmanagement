package com.yasinyazici.studentmanagement.beans;

import com.yasinyazici.studentmanagement.data.factory.IStudentDaoFactory;
import com.yasinyazici.studentmanagement.data.factory.IUniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.service.EnrollmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentServiceBean {

    private final EnrollmentService enrollmentService;


    public StudentServiceBean(IUniversityEnrollmentFactory universityEnrollmentFactory, IStudentDaoFactory studentDaoFactory) {
        this.enrollmentService = new EnrollmentService(universityEnrollmentFactory, studentDaoFactory);
    }

    @Bean
    EnrollmentService studentService() {
        return enrollmentService;
    }
}
