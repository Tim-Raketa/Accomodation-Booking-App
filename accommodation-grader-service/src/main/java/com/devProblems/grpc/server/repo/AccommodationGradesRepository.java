package com.devProblems.grpc.server.repo;

import com.devProblems.grpc.server.model.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccommodationGradesRepository extends MongoRepository<Grade, Integer>{

}


