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
    @DeleteMapping(value="/")
    public Boolean deleteGrade(@RequestBody CreateGradeDTO createDTO){
        return service.DeleteGrade(createDTO);
    }

    @GetMapping(value="/{acc_id}")
    public List<CreateGradeDTO> getGradesForAccommodation(@PathVariable Integer acc_id){
        return service.getGrades(acc_id);
    }
    @GetMapping(value="/{acc_id}/{username}")
    //Reci milosu samo da vidi kako se zove za username
    public CreateGradeDTO getSpecificGrade(@PathVariable Integer acc_id,@PathVariable String username){
        return service.getSpecificGrade(acc_id,username);
    }
    @GetMapping(value="/avg/{acc_id}")
    public Double getAvgGrade(@PathVariable Integer acc_id){
        return service.getAvgGrade(acc_id);
    }

}
