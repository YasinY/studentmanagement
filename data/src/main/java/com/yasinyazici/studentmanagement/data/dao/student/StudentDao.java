package com.yasinyazici.studentmanagement.data.dao.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "students")
@ToString
public class StudentDao {

    @Id
    private String id;

    private String name;

    private String address;

    private UniversityEnrollment universityEnrollment;

}
