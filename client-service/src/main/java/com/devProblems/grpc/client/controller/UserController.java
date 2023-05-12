package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.service.BookAuthorClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    final BookAuthorClientService bookAuthorClientService;
}
