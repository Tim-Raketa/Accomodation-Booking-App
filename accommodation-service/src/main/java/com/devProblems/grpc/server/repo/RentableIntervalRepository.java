package com.devProblems.grpc.server.repo;

import com.devProblems.grpc.server.model.Accommodation;
import com.devProblems.grpc.server.model.RentableInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface RentableIntervalRepository extends JpaRepository<RentableInterval, Long>{
    List<RentableInterval> findAllByAccommodationId(Long id);
    RentableInterval findByAccommodationIdAndStartTimeAfterAndEndTimeBefore
            (Long accommodationId,LocalDate start, LocalDate end);
}
