package com.devProblems.grpc.client.DTO;

import com.devProblems.UserReq;
import com.devProblems.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String residency;
    private UserType type;

    public UserDTO(UserReq req){
        this.username = req.getUsername();
        this.password = req.getPassword();
        this.name = req.getName();
        this.surname = req.getSurname();
        this.email = req.getEmail();
        this.residency = req.getResidency();
        this.type = req.getType();
    }
}
