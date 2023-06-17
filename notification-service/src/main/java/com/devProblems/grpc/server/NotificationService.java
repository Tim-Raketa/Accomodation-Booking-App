package com.devProblems.grpc.server;

import com.devProblems.grpc.server.model.Notification;
import com.devProblems.grpc.server.repository.NotificationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NotificationRepository notificationsRepository;
    public void notifyUser(String email){
        simpMessagingTemplate.convertAndSend("/notify/"+email,"New notification was added to the db.");
    }

    public Notification addNotification(Notification notification){
        notification.setOpened(false);
        notification = notificationsRepository.save(notification);
        notifyUser(notification.getUserToNotify());
        return notification;
    }
    public Notification openNotification(Long id) {
        for(Notification n : notificationsRepository.findAll()){
            if(n.getId().equals(id)){
                n.setOpened(true);
                notificationsRepository.save(n);
                notifyUser(n.getUserToNotify());
                return n;
            }
        }
        return null;
    }
}
