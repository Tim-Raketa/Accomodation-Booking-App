package com.devProblems.grpc.client.controller;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.CreateGradeDTO;
import com.devProblems.grpc.client.service.AccommodationGraderService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/accommodations/grader", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationGraderController {

    AccommodationGraderService service;
    @PostMapping(value="/")
    public Boolean createGrade(@RequestBody CreateGradeDTO createDTO){
            return service.CreateGrade(createDTO);
    }
    @PutMapping(value="/")
    public Boolean updateGrade(@RequestBody CreateGradeDTO createDTO){
        return service.UpdateGrade(createDTO);
    }
    @DeleteMapping(value="/{accommodationId}/{username}")
    public Boolean deleteGrade(@PathVariable Integer accommodationId,@PathVariable String username){
        CreateGradeDTO createGrade= new CreateGradeDTO();
        createGrade.setAccommodationId(accommodationId);
        createGrade.setUsername(username);
        return service.DeleteGrade(createGrade);
    }

    @GetMapping(value="/{acc_id}")
    public List<CreateGradeDTO> getGradesForAccommodation(@PathVariable Integer acc_id){
        return service.getGrades(acc_id);
    }
    @GetMapping(value="/username={username}")
    public List<CreateGradeDTO> getGradesForUsers(@PathVariable String username){
        return service.getGradesForUser(username);
    }
    @GetMapping(value="/{acc_id}/{username}")
    public CreateGradeDTO getSpecificGrade(@PathVariable Integer acc_id,@PathVariable String username){
        return service.getSpecificGrade(acc_id,username);
    }
    @GetMapping(value="/avg/{acc_id}")
    public Double getAvgGrade(@PathVariable Integer acc_id){
        return service.getAvgGrade(acc_id);
    }

}
