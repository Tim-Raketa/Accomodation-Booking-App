package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.AccommodationDTO;
import com.devProblems.grpc.client.service.AccommodationClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationController {
    AccommodationClientService accommodationClientService;

    @PostMapping(value = "/")
    public AccommodationDTO createAccommodation(@RequestBody AccommodationDTO accommodation){
        return accommodationClientService.createAccommodation(accommodation);
    }

    @GetMapping("/")
    public List<AccommodationDTO> getAllAccommodations(){
        return accommodationClientService.getAllAccommodations();
    }
}
