package com.yasinyazici.studentmanagement.beans;

import com.yasinyazici.studentmanagement.data.factory.IUniversityEnrollmentFactory;
import com.yasinyazici.studentmanagement.data.factory.UniversityEnrollmentFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UniversityEnrollmentFactoryBean {

    private final IUniversityEnrollmentFactory universityEnrollmentFactory = new UniversityEnrollmentFactory();

    @Bean
    public IUniversityEnrollmentFactory universityEnrollmentFactory() {
        return universityEnrollmentFactory;
    }

}
