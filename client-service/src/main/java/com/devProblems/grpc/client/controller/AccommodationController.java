package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.*;
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
    @DeleteMapping (value = "/{id}")
    public boolean deleteAccommodation(@PathVariable Long id){
        return accommodationClientService.deleteAccommodation(id);
    }
    @GetMapping("/")
    public List<AccommodationDTO> getAllAccommodations(){
        return accommodationClientService.getAllAccommodations();
    }

    @GetMapping("/host/{hostId}")
    public List<AccommodationDTO> getAccommodationsByHostId(@PathVariable String hostId){
        return accommodationClientService.getAccommodationsByHostId(hostId);
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
    @PostMapping("/search")
    public List<SearchResultDTO> search(@RequestBody SearchRequestDTO searchReq){
        return accommodationClientService.search(searchReq);
    }
    @PostMapping("/filter")
    public List<SearchResultDTO> filter(@RequestBody FilterRequestDTO filterReq){
        return accommodationClientService.filter(filterReq);
    }
}
