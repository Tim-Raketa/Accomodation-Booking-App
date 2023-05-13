package com.devProblems.grpc.server.repo;

import com.devProblems.grpc.server.model.Accommodation;
import com.devProblems.grpc.server.model.RentableInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface RentableIntervalRepository extends JpaRepository<RentableInterval, Long>{

}
