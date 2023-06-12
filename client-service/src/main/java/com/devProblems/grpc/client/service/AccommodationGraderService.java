package com.devProblems.grpc.client.service;

import com.devProblems.AccommodationGraderServiceGrpc;
import com.devProblems.AccommodationServiceGrpc;
import com.devProblems.Successful;
import com.devProblems.UserServiceGrpc;
import com.devProblems.grpc.client.DTO.CreateGradeDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AccommodationGraderService {
    @GrpcClient("grpc-accommodation-grader-service")
    AccommodationGraderServiceGrpc.AccommodationGraderServiceBlockingStub synchronousGrader;

    public Boolean CreateGrade(CreateGradeDTO grade){
        Successful resp=synchronousGrader.createGrade(grade.convertToMsg());
        return resp.getSuccess();
    }
    public Boolean UpdateGrade(CreateGradeDTO grade){
        Successful resp=synchronousGrader.updateGrade(grade.convertToMsg());
        return resp.getSuccess();
    }
    public Boolean DeleteGrade(CreateGradeDTO grade){
        Successful resp=synchronousGrader.deleteGrade(grade.convertToMsg());
        return resp.getSuccess();
    }

}
