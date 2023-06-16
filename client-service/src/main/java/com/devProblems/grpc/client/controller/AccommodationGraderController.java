package com.devProblems.grpc.client.controller;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.CreateGradeDTO;
import com.devProblems.grpc.client.DTO.CreateHostGradeDTO;
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

    @PostMapping(value = "/host")
    public Boolean createHostGrade(@RequestBody CreateHostGradeDTO createDTO) {
        return service.CreateHostGrade(createDTO);
    }

    @PutMapping(value="/")
    public Boolean updateGrade(@RequestBody CreateGradeDTO createDTO){
        return service.UpdateGrade(createDTO);
    }

    @PutMapping(value = "/host")
    public Boolean updateHostGrade(@RequestBody CreateHostGradeDTO createDTO){
        return service.UpdateHostGrade(createDTO);
    }

    @DeleteMapping(value="/{accommodationId}/{username}")
    public Boolean deleteGrade(@PathVariable Integer accommodationId,@PathVariable String username){
        CreateGradeDTO createGrade= new CreateGradeDTO();
        createGrade.setAccommodationId(accommodationId);
        createGrade.setUsername(username);
        return service.DeleteGrade(createGrade);
    }

    @DeleteMapping(value = "/host/{hostId}/{username}")
    public Boolean deleteHostGrade(@PathVariable String hostId, @PathVariable String username){
        CreateHostGradeDTO createHostGrade = new CreateHostGradeDTO();
        createHostGrade.setHostId(hostId);
        createHostGrade.setUsername(username);
        return service.DeleteHostGrade(createHostGrade);
    }

    @GetMapping(value="/{acc_id}")
    public List<CreateGradeDTO> getGradesForAccommodation(@PathVariable Integer acc_id){
        return service.getGrades(acc_id);
    }

    @GetMapping(value = "/host/{hostId}")
    public List<CreateHostGradeDTO> getGradesForHost(@PathVariable String hostId){
        return service.getHostGrades(hostId);
    }

    @GetMapping(value="/username={username}")
    public List<CreateGradeDTO> getGradesForUsers(@PathVariable String username){
        return service.getGradesForUser(username);
    }

    @GetMapping(value = "/host/username={username}")
    public List<CreateHostGradeDTO> getHostGradesByUser(@PathVariable String username){
        return service.getHostGradesByUser(username);
    }

    @GetMapping(value="/{acc_id}/{username}")
    public CreateGradeDTO getSpecificGrade(@PathVariable Integer acc_id,@PathVariable String username){
        return service.getSpecificGrade(acc_id,username);
    }

    @GetMapping(value = "/host/{hostId}/{username}")
    public CreateHostGradeDTO getSpecificHostGrade(@PathVariable String hostId, @PathVariable String username){
        return service.getSpecificHostGrade(hostId, username);
    }

    @GetMapping(value="/avg/{acc_id}")
    public Double getAvgGrade(@PathVariable Integer acc_id){
        return service.getAvgGrade(acc_id);
    }

    @GetMapping(value = "/host/avg/{hostId}")
    public Double getAvgHostGrade(@PathVariable String hostId){
        return service.getAvgHostGrade(hostId);
    }

}
