package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.CreateGradeDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Successful resp=synchronousGrader.deleteGrade(grade.convertToSpecificGrade());
        return resp.getSuccess();
    }

    public List<CreateGradeDTO> getGrades(Integer accId) {
        AllGrades grades=synchronousGrader.getAllGrades(GetAccommodationGrade.newBuilder().setAccommodationId(accId).build());
        return grades.getGradesList().stream().map(grade->new CreateGradeDTO(grade)).toList();
    }
    public List<CreateGradeDTO> getGradesForUser(String username) {
        AllGrades grades=synchronousGrader.getAllGradesUser(UserMsg.newBuilder().setUsername(username).build());
        return grades.getGradesList().stream().map(grade->new CreateGradeDTO(grade)).toList();
    }

    public CreateGradeDTO getSpecificGrade(Integer accId, String username) {
        CreateAccommodationGrade grade=synchronousGrader.getSpecificGrade(SpecificGrade.newBuilder()
                .setUsername(username).setAccommodationId(accId).build());
        return new CreateGradeDTO(grade);
    }

    public Double getAvgGrade(Integer accId) {
        Double avgGrade=synchronousGrader.getAvgGrade(GetAccommodationGrade.newBuilder().setAccommodationId(accId).build())
                .getGrade();
        return avgGrade;
    }

}
