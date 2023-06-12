package com.devProblems.grpc.server;

import com.devProblems.grpc.server.model.Grade;
import com.devProblems.grpc.server.repo.AccommodationGradesRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@GrpcService
public class AccommodationGraderService extends AccommodationGraderServiceGrpc.AccommodationGraderServiceImplBase {

    @Autowired
    AccommodationGradesRepository repository;
    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousClient;

    @Override
    public void createGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        boolean success=false;
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        if(grade.isEmpty()){
            repository.save(new Grade(request));
            success=true;
        }
        responseObserver.onNext(Successful.newBuilder().setSuccess(success).build());
        responseObserver.onCompleted();
    }


    @Override
    public void updateGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        boolean success=false;
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        if(grade.isPresent()){
            grade.get().setGrade(request.getGrade());
            grade.get().setTimeStamp(LocalDateTime.parse(request.getTime()));
            repository.save(grade.get());
            success=true;
        }
        responseObserver.onNext(Successful.newBuilder().setSuccess(success).build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        boolean success=false;
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        if(grade.isPresent()){
            repository.delete(grade.get());
            success=true;
        }
        responseObserver.onNext(Successful.newBuilder().setSuccess(success).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAvgGrade(GetAccommodationGrade request, StreamObserver<AccommodationGrade> responseObserver) {
        super.getAvgGrade(request, responseObserver);
    }

}
