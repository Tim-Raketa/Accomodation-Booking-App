package com.devProblems.grpc.server.model;

import com.devProblems.CreateAccommodationGrade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("grades")
@Data
public class Grade {
    @Transient
    public static final String SEQUENCE_NAME = "grades_sequence";
    @Id
    private Integer id;
    private Integer accommodationId;
    private String username;
    private Integer grade;
    private LocalDateTime timeStamp;

    public Grade(CreateAccommodationGrade accommodationGrade){
        this.accommodationId=accommodationGrade.getAccommodationId();
        this.username=accommodationGrade.getUsername();
        this.grade=accommodationGrade.getGrade();
        this.timeStamp=LocalDateTime.parse(accommodationGrade.getTime());
    }
    public CreateAccommodationGrade ToResp(){
        return CreateAccommodationGrade.newBuilder()
                .setAccommodationId(this.accommodationId).setGrade(this.grade)
                .setTime(this.timeStamp.toString()).setUsername(this.username)
                .build();
    }
}
