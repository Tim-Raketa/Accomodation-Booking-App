package com.devProblems.grpc.client.service;

import com.devProblems.Created;
import com.devProblems.UserReq;
import com.devProblems.UserServiceGrpc;
import com.devProblems.grpc.client.DTO.UserDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @GrpcClient("grpc-user-service")
    UserServiceGrpc.UserServiceBlockingStub synchronousUser;

    public boolean register(UserDTO userReq) {
        Created response = synchronousUser.register(
                UserReq.newBuilder()
                        .setUsername(userReq.getUsername())
                        .setPassword(userReq.getPassword())
                        .setName(userReq.getName())
                        .setSurname(userReq.getSurname())
                        .setEmail(userReq.getEmail())
                        .setResidency(userReq.getResidency())
                        .setType(userReq.getType())
                        .build());

        return response.getCreated();
    }
}
