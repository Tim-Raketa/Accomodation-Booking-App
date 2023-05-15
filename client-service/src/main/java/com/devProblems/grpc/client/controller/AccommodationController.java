package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.AccommodationDTO;
import com.devProblems.grpc.client.DTO.RentableIntervalDTO;
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
    @GetMapping(value = "/{id}")
    public AccommodationDTO getAccommodationById(@PathVariable Long id){
        return accommodationClientService.getById(id);
    }
    @GetMapping("/")
    public List<AccommodationDTO> getAllAccommodations(){
        return accommodationClientService.getAllAccommodations();
    }

    @GetMapping(value = "/getRentableInterval/{id}")
    public RentableIntervalDTO getRentableIntervalById(@PathVariable Long id){
        return accommodationClientService.getRentableIntervalById(id);
    }

    @PostMapping(value = "/createRentableInterval")
    public RentableIntervalDTO createRentableInterval(@RequestBody RentableIntervalDTO rentableInterval){
        return accommodationClientService.createRentableInterval(rentableInterval);
    }

    @PutMapping(value = "/updateRentableInterval")
    public RentableIntervalDTO updateRentableInterval(@RequestBody RentableIntervalDTO rentableInterval){
        return accommodationClientService.updateRentableInterval(rentableInterval);
    }

    @GetMapping("/rentableIntervals/accommodationId={accommodationId}")
    public List<RentableIntervalDTO> getRentableIntervalsByAccommodationId(@PathVariable Long accommodationId){
        return accommodationClientService.getRentableIntervalsByAccommodationId(accommodationId);
    }
}
