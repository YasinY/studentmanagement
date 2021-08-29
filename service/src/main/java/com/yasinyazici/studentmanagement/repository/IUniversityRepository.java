package com.yasinyazici.studentmanagement.repository;

import com.yasinyazici.studentmanagement.data.dao.university.UniversityDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUniversityRepository extends MongoRepository<UniversityDao, String> {

    public UniversityDao findByUniversityName(String name);

}
