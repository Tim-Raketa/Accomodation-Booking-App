package com.devProblems.grpc.server.model;

import com.devProblems.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document("users")
public class User {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String residency;
    private UserType type;
}
