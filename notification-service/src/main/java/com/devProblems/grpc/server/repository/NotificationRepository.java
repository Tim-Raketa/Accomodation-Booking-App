package com.devProblems.grpc.server.repository;

import com.devProblems.grpc.server.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, Integer> {
}
