package com.devProblems.grpc.client.controller;

import com.devProblems.Notification;
import com.devProblems.grpc.client.DTO.NotificationDTO;
import com.devProblems.grpc.client.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notifs", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class NotificationController {
    NotificationService notificationService;
    @GetMapping("/{email}")
    public List<NotificationDTO> getNotifications(@PathVariable String email){
        return notificationService.getAllNotifications(email);
    }
    @PostMapping ("/")
    public NotificationDTO newNotifications(@RequestBody NotificationDTO notificationDTO){
        return notificationService.addNotification(notificationDTO);
    }
    @PutMapping ("/{id}")
    public NotificationDTO openNotifications(@PathVariable Integer id){
        return notificationService.openNotification(id);
    }

}
