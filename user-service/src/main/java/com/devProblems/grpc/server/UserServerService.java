package com.devProblems.grpc.server;

import com.devProblems.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase{
    @Override
    public void getUser(User request, StreamObserver<User> responseObserver) {
        TempUser.getUsersTempDB()
                .stream()
                .filter(user -> user.getUsername() == request.getUsername())
                .findFirst()
                .ifPresent(responseObserver::onNext);        //On next uvek treba
        responseObserver.onCompleted();// on completed uvek treba
    }


}
