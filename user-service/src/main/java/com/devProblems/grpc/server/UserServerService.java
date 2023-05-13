package com.devProblems.grpc.server;

import com.devProblems.*;
import com.devProblems.grpc.server.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.devProblems.grpc.server.model.User;

@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase{
    @Autowired
    UserRepository repository;

    @Override
    public void register(UserReq request, StreamObserver<Created> responseObserver) {
        User newUser = new User(request);
        newUser = repository.save(newUser);
        responseObserver.onNext(
                Created.newBuilder()
                        .setCreated(true).build())
        ;
        responseObserver.onCompleted();
    }
}
