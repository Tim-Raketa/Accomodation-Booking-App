package com.devProblems.grpc.server.model;

import com.devProblems.CreateNotification;
import com.devProblems.ReturnNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("notifications")
public class Notification {
    @Transient
    public static final String SEQUENCE_NAME = "notification_sequence";
    @Id
    private Integer id;

    private String title;

    private String message;

    private String userToNotify;

    private boolean opened;

    public ReturnNotification convert(){
        return ReturnNotification.newBuilder()
                .setId(this.id).setOpened(this.opened).setUserToNotify(this.getUserToNotify())
                .setMessage(this.getMessage()).setTitle(this.getTitle()).build();
    }
    public Notification(CreateNotification createNotification){
        this.title = createNotification.getTitle();
        this.message = createNotification.getMessage();
        this.userToNotify = createNotification.getUserToNotify();
        this.opened = false;
    }
}
