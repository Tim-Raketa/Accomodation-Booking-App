package com.devProblems.grpc.server.repo;

import com.devProblems.grpc.server.model.HostGrade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HostGradesRepository extends MongoRepository<HostGrade, Integer> {
    Optional<HostGrade> findByHostIdAndUsername(String hostId, String username);
    List<HostGrade> findAllByHostId(String hostId);
    List<HostGrade> findAllByUsername(String username);
}
