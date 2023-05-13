package com.devProblems.grpc.server;

import com.devProblems.*;
import com.devProblems.grpc.server.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.devProblems.grpc.server.model.User;

import java.util.Optional;

@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase{
    @Autowired
    UserRepository repository;

    @Override
    public void register(UserReq request, StreamObserver<Created> responseObserver) {
        User newUser = new User(request);
        Optional<User> tempUser = repository.findById(newUser.getUsername());
        if (tempUser.isEmpty()) {
            newUser = repository.save(newUser);
            responseObserver.onNext(Created.newBuilder().setCreated(true).build());
        }else{
            responseObserver.onNext(Created.newBuilder().setCreated(false).build());
        }
        responseObserver.onCompleted();
    }
}
