package com.yasinyazici.studentmanagement.beans;

import com.yasinyazici.studentmanagement.data.dao.student.StudentDao;
import com.yasinyazici.studentmanagement.data.dto.student.StudentDto;
import com.yasinyazici.studentmanagement.data.factory.IStudentDtoFactory;
import com.yasinyazici.studentmanagement.mapper.IMapper;
import com.yasinyazici.studentmanagement.mapper.StudentDaoToDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentDaoToDtoMapperBean {

    private IMapper<StudentDao, StudentDto> studentDaoToDtoMapper;

    public StudentDaoToDtoMapperBean(IStudentDtoFactory studentDtoFactory) {
        this.studentDaoToDtoMapper = new StudentDaoToDtoMapper(studentDtoFactory);
    }

    @Bean
    public IMapper<StudentDao, StudentDto> studentDaoToDtoMapper() {
        return studentDaoToDtoMapper;
    }
}
