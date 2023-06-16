package com.devProblems.grpc.server.model;

import com.devProblems.CreateHostGrade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("host_grades")
@Data
public class HostGrade {

    @Transient
    public static final String SEQUENCE_NAME = "host_grades_sequence";
    @Id
    private Integer id;
    private String hostId;
    private String username;
    private Integer grade;
    private LocalDateTime timeStamp;

    public HostGrade(CreateHostGrade hostGrade){
        this.hostId = hostGrade.getHostId();
        this.username = hostGrade.getUsername();
        this.grade = hostGrade.getGrade();
        this.timeStamp = LocalDateTime.parse(hostGrade.getTime());
    }

    public CreateHostGrade ToResp(){
        return CreateHostGrade.newBuilder()
                .setHostId(this.hostId)
                .setUsername(this.username)
                .setGrade(this.grade)
                .setTime(this.timeStamp.toString())
                .build();
    }
}
