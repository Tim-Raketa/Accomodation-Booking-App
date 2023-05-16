package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.EditDTO;
import com.devProblems.grpc.client.DTO.LoginDTO;
import com.devProblems.grpc.client.DTO.UserDTO;
import com.devProblems.grpc.client.DTO.UserTokenState;
import com.devProblems.grpc.client.service.BookAuthorClientService;
import com.devProblems.grpc.client.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    final UserService userService;

    @PostMapping(value = "/register")
    public boolean register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

    @PostMapping(value = "/login")
    public UserTokenState login(@RequestBody LoginDTO loginDTO) { return userService.login(loginDTO); }

    @GetMapping(value = "/getUser/{username}")
    public UserDTO getUser(@PathVariable String username) { return userService.getUser(username); }

    @PostMapping(value = "/edit")
    public UserTokenState edit(@RequestBody EditDTO editDTO){
        return userService.edit(editDTO);
    }
    @DeleteMapping(value = "/{username}")
    public Boolean delete(@PathVariable String username){
        return userService.delete(username);
    }
}
