package com.devProblems.grpc.client.DTO;

import com.devProblems.CreateAccommodationGrade;
import com.devProblems.SpecificGrade;
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
    String timeStamp;

    public CreateGradeDTO(CreateAccommodationGrade grade){
        this.accommodationId=grade.getAccommodationId();
        this.username=grade.getUsername();
        this.grade=grade.getGrade();
        this.timeStamp=grade.getTime();
    }
    public CreateAccommodationGrade convertToMsg(){
       return CreateAccommodationGrade.newBuilder()
                .setAccommodationId(this.getAccommodationId()).setUsername(this.getUsername())
                .setGrade(this.grade).setTime(timeStamp).build();
    }
    public SpecificGrade convertToSpecificGrade(){
        return SpecificGrade.newBuilder()
                .setAccommodationId(this.getAccommodationId()).setUsername(this.getUsername())
                .build();
    }


}
