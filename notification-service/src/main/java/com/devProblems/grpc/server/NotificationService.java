package com.devProblems.grpc.server;

import com.devProblems.grpc.server.model.Notification;
import com.devProblems.grpc.server.repository.NotificationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@GrpcService
public class NotificationService extends NotificationServiceGrpc.NotificationServiceImplBase {

    @Autowired
    SequenceGeneratorService seqGen;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NotificationRepository notificationsRepository;
    @GrpcClient("grpc-user-service")
    UserServiceGrpc.UserServiceBlockingStub synchronousClient;

    @Override
    public void getAllNotifications(CreateNotification request, StreamObserver<NotificationList> responseObserver) {
        List<Notification> foundNotifications=notificationsRepository.findAll().stream()
                .filter(notification->notification.getUserToNotify().equals(request.getUserToNotify())).toList();

        List<ReturnNotification> response=foundNotifications.stream().map(notification -> notification.convert()).toList();
        responseObserver.onNext(NotificationList.newBuilder().addAllNotifications(response).build());
        responseObserver.onCompleted();

    }

    @Override
    public void addNotifications(CreateNotification request, StreamObserver<ReturnNotification> responseObserver) {
        Notification notification=new Notification(request);
        notification.setId(seqGen.getSequenceNumber(notification.SEQUENCE_NAME));

        UserReq user=synchronousClient.getUser(UserId.newBuilder().setUsername(request.getUserToNotify()).build());

        Boolean goodType=false;
        String[] arrOfStr = user.getNotificationTypes().split(",", 8);
        for (String a : arrOfStr)
            if(request.getTitle().equals(a))
            {
                goodType=true;
                notificationsRepository.save(notification);
                notifyUser(notification.getUserToNotify());
                break;
            }
        if(goodType)
            responseObserver.onNext(notification.convert());
        else
            responseObserver.onNext(ReturnNotification.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void notifyUser(String email){
        simpMessagingTemplate.convertAndSend("/notify/"+email,"New notification was added to the db.");
    }

    @Override
    public void openNotifications(NotificationId request, StreamObserver<ReturnNotification> responseObserver) {
        Notification notification=notificationsRepository.findById(request.getId()).get();
        notification.setOpened(true);
        notificationsRepository.save(notification);

        responseObserver.onNext(notification.convert());
        responseObserver.onCompleted();
    }
}
