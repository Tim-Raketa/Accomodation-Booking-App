package com.devProblems.grpc.server.model;

import com.devProblems.AccommodationGrade;
import com.devProblems.CreateAccommodationGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("grades")
public class Grade {

    Integer accommodationId;
    String username;
    Integer grade;
    LocalDateTime timeStamp;

    public Grade(CreateAccommodationGrade accommodationGrade){
        this.accommodationId=accommodationGrade.getAccommodationId();
        this.username=accommodationGrade.getUsername();
        this.grade=accommodationGrade.getGrade();
        this.timeStamp=LocalDateTime.parse(accommodationGrade.getTime());
    }
}
