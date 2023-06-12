package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.CreateGradeDTO;
import com.devProblems.grpc.client.service.AccommodationGraderService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


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

}
