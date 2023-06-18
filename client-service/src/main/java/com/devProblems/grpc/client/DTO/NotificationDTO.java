package com.devProblems.grpc.client.DTO;

import com.devProblems.CreateNotification;
import com.devProblems.ReturnNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDTO {
    private Integer id;

    private String title;

    private String message;

    private String userToNotify;

    private boolean opened;

    public NotificationDTO(ReturnNotification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
        this.userToNotify = notification.getUserToNotify();
        this.opened = notification.getOpened();
    }
    public CreateNotification convertToReq(){
        return CreateNotification.newBuilder().setUserToNotify(this.userToNotify)
                .setMessage(this.message).setTitle(this.title).build();
    }
}
