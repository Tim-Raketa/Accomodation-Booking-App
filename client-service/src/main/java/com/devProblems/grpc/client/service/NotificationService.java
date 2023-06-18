package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.NotificationDTO;
import com.devProblems.grpc.client.DTO.ReservationDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @GrpcClient("grpc-notification-service")
    NotificationServiceGrpc.NotificationServiceBlockingStub synchronousNotification;
    public List<NotificationDTO> getAllNotifications(String email) {
        NotificationList response=synchronousNotification.getAllNotifications(CreateNotification.newBuilder()
                        .setUserToNotify(email).build());
        var notifications=response.getNotificationsList().stream().map(notification -> new NotificationDTO(notification)).toList();
        return notifications;
    }

    public NotificationDTO addNotification(NotificationDTO notificationDTO) {
        ReturnNotification response = synchronousNotification.addNotifications(notificationDTO.convertToReq());
        return new NotificationDTO(response);
    }

    public NotificationDTO openNotification(Integer id) {
        ReturnNotification response=synchronousNotification.openNotifications(NotificationId.newBuilder().setId(id).build());
        return new NotificationDTO(response);
    }
}
