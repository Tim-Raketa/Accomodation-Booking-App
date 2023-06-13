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
    SequenceGeneratorService seqGen;
    @Autowired
    AccommodationGradesRepository repository;
    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousClient;

    @Override
    public void createGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        boolean success=false;
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        if(grade.isEmpty()){
            Grade grades=new Grade(request);
            grades.setId(seqGen.getSequenceNumber(grades.SEQUENCE_NAME));
            repository.save(grades);
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
    public void deleteGrade(SpecificGrade request, StreamObserver<Successful> responseObserver) {
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
    public void getSpecificGrade(SpecificGrade request, StreamObserver<CreateAccommodationGrade> responseObserver) {
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        responseObserver.onNext(grade.get().ToResp());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllGrades(GetAccommodationGrade request, StreamObserver<AllGrades> responseObserver) {
        responseObserver.onNext(AllGrades.newBuilder()
                .addAllGrades(repository.findAllByAccommodationId(request.getAccommodationId())
                        .stream().map(Grade::ToResp).toList())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAvgGrade(GetAccommodationGrade request, StreamObserver<AccommodationGrade> responseObserver) {
        Double average=repository.findAll().stream()
                .filter(grade-> grade.getAccommodationId()==request.getAccommodationId())
                .mapToInt(grade->grade.getGrade()).average().getAsDouble();
            responseObserver.onNext(AccommodationGrade.newBuilder().setGrade(average).build());
            responseObserver.onCompleted();
    }

}
