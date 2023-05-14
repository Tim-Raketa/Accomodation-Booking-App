package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.LoginDTO;
import com.devProblems.grpc.client.DTO.UserDTO;
import com.devProblems.grpc.client.DTO.UserTokenState;
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
    public UserTokenState login(LoginDTO loginDTO) {
        UserTokenStateRes response = synchronousUser.login(
                LoginReq.newBuilder()
                        .setUsername(loginDTO.getUsername())
                        .setPassword(loginDTO.getPassword())
                        .build());

        return new UserTokenState(response);
    }

}
