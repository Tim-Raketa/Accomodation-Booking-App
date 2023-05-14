package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTokenState {
    private String accessToken;
    private String role;

    public UserTokenState(com.devProblems.UserTokenStateRes userToken){
        this.accessToken = userToken.getAccessToken();
        this.role = userToken.getRole();
    }
}
