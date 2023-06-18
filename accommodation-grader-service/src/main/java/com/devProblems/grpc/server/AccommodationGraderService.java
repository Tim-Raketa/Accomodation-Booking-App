package com.devProblems.grpc.server;

import com.devProblems.grpc.server.model.Grade;
import com.devProblems.grpc.server.model.HostGrade;
import com.devProblems.grpc.server.repo.AccommodationGradesRepository;
import com.devProblems.grpc.server.repo.HostGradesRepository;
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
    @Autowired
    HostGradesRepository hostGradesRepository;

    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousClient;
    @GrpcClient("grpc-notification-service")
    NotificationServiceGrpc.NotificationServiceBlockingStub synchronousNotification;

    @Override
    public void createGrade(CreateAccommodationGrade request, StreamObserver<Successful> responseObserver) {
        boolean success=false;
        Optional<Grade> grade=repository.findByAccommodationIdAndUsername(request.getAccommodationId(),request.getUsername());
        if(grade.isEmpty()){
            Grade newGrade=new Grade(request);
            newGrade.setId(seqGen.getSequenceNumber(newGrade.SEQUENCE_NAME));
            repository.save(newGrade);
            success=true;
            AccommodationResp accommodation=synchronousClient.getById(AccommodationIdReq.newBuilder().setId(newGrade.getAccommodationId()).build());
            String hostId=accommodation.getHostId();
            synchronousNotification.addNotifications(CreateNotification.newBuilder()
                    .setMessage("Accommodation "+accommodation.getName()+" has been graded by "+grade.get().getUsername())
                    .setTitle("Grading")
                    .setUserToNotify(hostId)
                    .build()
            );
        }


        responseObserver.onNext(Successful.newBuilder().setSuccess(success).build());
        responseObserver.onCompleted();
    }

    @Override
    public void createGradeForHost(CreateHostGrade request, StreamObserver<Successful> responseObserver){
        boolean success = false;
        Optional<HostGrade> grade = hostGradesRepository.findByHostIdAndUsername(request.getHostId(), request.getUsername());
        if(grade.isEmpty()){
            HostGrade hostGrade = new HostGrade(request);
            hostGrade.setId(seqGen.getSequenceNumber(hostGrade.SEQUENCE_NAME));
            hostGradesRepository.save(hostGrade);
            success = true;
            synchronousNotification.addNotifications(CreateNotification.newBuilder()
                    .setMessage("User "+hostGrade.getUsername()+" has graded you ")
                    .setTitle("Grading")
                    .setUserToNotify(hostGrade.getHostId())
                    .build()
            );
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
    public void updateHostGrade(CreateHostGrade request, StreamObserver<Successful> responseObserver){
        boolean success = false;
        Optional<HostGrade> grade = hostGradesRepository.findByHostIdAndUsername(request.getHostId(), request.getUsername());
        if(grade.isPresent()){
            grade.get().setGrade(request.getGrade());
            grade.get().setTimeStamp(LocalDateTime.parse(request.getTime()));
            hostGradesRepository.save(grade.get());
            success = true;
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
    public void deleteHostGrade(SpecificHostGrade request, StreamObserver<Successful> responseObserver){
        boolean success = false;
        Optional<HostGrade> grade = hostGradesRepository.findByHostIdAndUsername(request.getHostId(), request.getUsername());
        if(grade.isPresent()){
            hostGradesRepository.delete(grade.get());
            success = true;
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
    public void getSpecificHostGrade(SpecificHostGrade request, StreamObserver<CreateHostGrade> responseObserver){
        Optional<HostGrade> grade = hostGradesRepository.findByHostIdAndUsername(request.getHostId(), request.getUsername());
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
    public void getAllHostGrades(GetHostGrade request, StreamObserver<AllHostGrades> responseObserver){
        responseObserver.onNext(AllHostGrades.newBuilder()
                .addAllGrades(hostGradesRepository.findAllByHostId(request.getHostId())
                        .stream().map(HostGrade::ToResp).toList())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllGradesUser(UserMsg request, StreamObserver<AllGrades> responseObserver) {
        responseObserver.onNext(AllGrades.newBuilder()
                .addAllGrades(repository.findAllByUsername(request.getUsername())
                        .stream().map(Grade::ToResp).toList())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllHostGradesByUser(UserMsg request, StreamObserver<AllHostGrades> responseObserver){
        responseObserver.onNext(AllHostGrades.newBuilder()
                .addAllGrades(hostGradesRepository.findAllByUsername(request.getUsername())
                        .stream().map(HostGrade::ToResp).toList())
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

    @Override
    public void getAvgHostGrade(GetHostGrade request, StreamObserver<HostGradeValue> responseObserver){
        Double average = hostGradesRepository.findAll().stream()
                .filter(grade -> grade.getHostId().equals(request.getHostId()))
                .mapToInt(grade -> grade.getGrade()).average().getAsDouble();
        responseObserver.onNext(HostGradeValue.newBuilder().setGrade(average).build());
        responseObserver.onCompleted();
    }

}
