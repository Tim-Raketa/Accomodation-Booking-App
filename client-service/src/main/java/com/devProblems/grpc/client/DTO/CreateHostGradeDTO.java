package com.devProblems.grpc.client.DTO;

import com.devProblems.CreateHostGrade;
import com.devProblems.SpecificHostGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateHostGradeDTO {
    String hostId;
    String username;
    Integer grade;
    String timeStamp;



    public CreateHostGradeDTO(CreateHostGrade grade){
        this.hostId = grade.getHostId();
        this.username = grade.getUsername();
        this.grade = grade.getGrade();
        this.timeStamp = grade.getTime();
    }

    public CreateHostGrade convertToMsg(){
        return CreateHostGrade.newBuilder()
                .setHostId(this.getHostId())
                .setUsername(this.getUsername())
                .setGrade(this.getGrade())
                .setTime(this.getTimeStamp())
                .build();
    }

    public SpecificHostGrade convertToSpecificHostGrade(){
        return SpecificHostGrade.newBuilder()
                .setHostId(this.getHostId())
                .setUsername(this.getUsername())
                .build();
    }

}
