package com.devProblems.grpc.server;

import com.devProblems.grpc.server.repo.AccommodationGradesRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.*;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AccommodationGraderService extends AccommodationGraderServiceGrpc.AccommodationGraderServiceImplBase {

    @Autowired
    AccommodationGradesRepository repository;
    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousClient;

    @Override
    public void createGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        super.createGrade(request, responseObserver);
    }

    @Override
    public void updateGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        super.updateGrade(request, responseObserver);
    }

    @Override
    public void deleteGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        super.deleteGrade(request, responseObserver);
    }

    @Override
    public void getAvgGrade(GetAccommodationGrade request, StreamObserver<AccommodationGrade> responseObserver) {
        super.getAvgGrade(request, responseObserver);
    }
}
