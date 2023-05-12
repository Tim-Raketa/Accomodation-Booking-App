package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.CreateReservationDTO;
import com.devProblems.grpc.client.DTO.ReservationDTO;
import com.devProblems.grpc.client.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationsController {
    ReservationService reservationService;


    @GetMapping("/")
    public List<ReservationDTO> getReservations(){
        return reservationService.getAllReservations();
    }
    @PostMapping(value = "/")
    public ReservationDTO addReservations(@RequestBody CreateReservationDTO res){
        return reservationService.addReservations(res);
    }
    @PutMapping(value = "/")
    public ReservationDTO updateReservations(@RequestBody ReservationDTO res){
        return reservationService.updateReservation(res);
    }
    @PostMapping ("/available")
    public Boolean IsAvailable(@RequestBody CreateReservationDTO res){
        return reservationService.isAvailable(res);
    }


}
