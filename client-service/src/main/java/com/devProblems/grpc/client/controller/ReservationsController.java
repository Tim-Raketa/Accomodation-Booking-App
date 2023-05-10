package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.ReservationDTO;
import com.devProblems.grpc.client.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
