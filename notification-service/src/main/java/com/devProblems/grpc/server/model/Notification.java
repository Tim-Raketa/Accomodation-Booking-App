package com.devProblems.grpc.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    @Transient
    public static final String SEQUENCE_NAME = "notification_sequence";
    @Id
    private Integer id;

    private String title;

    private String message;

    private String userToNotify;

    private boolean opened;
}
