package com.devProblems.grpc.server.repo;

import com.devProblems.grpc.server.model.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccommodationGradesRepository extends MongoRepository<Grade, Integer>{
    Optional<Grade> findByAccommodationIdAndUsername(Integer id, String username);
}


