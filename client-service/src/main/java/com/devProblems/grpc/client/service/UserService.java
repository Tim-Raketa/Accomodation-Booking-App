package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.EditDTO;
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
                        .setCancelCount(0)
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

    public UserDTO getUser(String username) {
        UserReq response = synchronousUser.getUser(
                UserId.newBuilder()
                        .setUsername(username)
                        .build());

        return new UserDTO(response);
    }

    public UserTokenState edit(EditDTO editReq) {
        UserTokenStateRes response = synchronousUser.edit(
                EditReq.newBuilder()
                        .setUsername(editReq.getUsername())
                        .setPassword(editReq.getPassword())
                        .setName(editReq.getName())
                        .setSurname(editReq.getSurname())
                        .setEmail(editReq.getEmail())
                        .setResidency(editReq.getResidency())
                        .setType(editReq.getType())
                        .setOldUsername(editReq.getOldUsername())
                        .setNotificationTypes(editReq.getNotificationTypes())
                        .build());

        return new UserTokenState(response);
    }

    public Boolean delete(String username) {
        Created response =synchronousUser.deleteUser(UserId.newBuilder()
                .setUsername(username).build());

        return response.getCreated();
    }

    public Boolean getProminentStatus(String username) {
        Prominent response = synchronousUser.getProminentStatus(
                UserId.newBuilder()
                        .setUsername(username)
                        .build());

        return response.getProminent();
    }
}
