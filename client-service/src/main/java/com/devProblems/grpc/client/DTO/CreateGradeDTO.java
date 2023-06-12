package com.devProblems.grpc.client.DTO;

import com.devProblems.CreateAccommodationGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateGradeDTO {

    Integer accommodationId;
    String username;
    Integer grade;
    LocalDateTime timeStamp;

    public CreateAccommodationGrade convertToMsg(){
       return CreateAccommodationGrade.newBuilder()
                .setAccommodationId(this.getAccommodationId()).setUsername(this.getUsername())
                .setGrade(this.grade).setTime(timeStamp.toString()).build();
    }
}
