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

    @Override
    public void login(LoginReq request, StreamObserver<UserTokenStateRes> responseObserver) {
        Optional<User> tempUser = repository.findById(request.getUsername());
        if (tempUser.isEmpty() || !request.getPassword().equals(tempUser.get().getPassword())) {
            responseObserver.onNext(UserTokenStateRes.newBuilder()
                    .setAccessToken("")
                    .setRole("")
                    .build());
        } else {
            responseObserver.onNext(UserTokenStateRes.newBuilder()
                    .setAccessToken(tempUser.get().getUsername())
                    .setRole(tempUser.get().getType().toString())
                    .build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserId request, StreamObserver<UserReq> responseObserver) {
        Optional<User> tempUser = repository.findById(request.getUsername());
        responseObserver.onNext(
                UserReq.newBuilder()
                    .setUsername(tempUser.get().getUsername())
                    .setPassword(tempUser.get().getPassword())
                    .setName(tempUser.get().getName())
                    .setSurname(tempUser.get().getSurname())
                    .setEmail(tempUser.get().getEmail())
                    .setResidency(tempUser.get().getResidency())
                    .setType(tempUser.get().getType())
                    .build());
        responseObserver.onCompleted();
    }
}
