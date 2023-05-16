package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.CreateReservationDTO;
import com.devProblems.grpc.client.DTO.PendingDTO;
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
    @DeleteMapping(value = "/{id}")
    public Boolean DeleteReservations(@PathVariable Long id){
        return reservationService.DeleteReservation(id);
    }
    @DeleteMapping(value = "/deny/{id}")
    public Boolean DenyReservations(@PathVariable Long id){
        return reservationService.DenyReservation(id);
    }
    @PutMapping(value = "/cancel/{id}")
    public Boolean CancelReservations(@PathVariable Long id){
        return reservationService.CancelReservation(id);
    }
    @PutMapping(value = "/accept/{id}")
    public Boolean AcceptReservations(@PathVariable Long id){
        return reservationService.AcceptReservation(id);
    }
    @GetMapping("/pending/accommodation={accommodationId}")
    public List<PendingDTO> getReservationsPending(@PathVariable Long accommodationId){
        return reservationService.getAllPending(accommodationId);
    }
    @GetMapping("/pending/username={username}")
    public List<PendingDTO> getReservationsPendingUser(@PathVariable String username){
        return reservationService.getAllPending(username);
    }
    @GetMapping("/accept/username={username}")
    public List<PendingDTO> getReservationsAccepted(@PathVariable String username){
        return reservationService.getAllAccepted(username);
    }
    @PostMapping ("/available")
    public Boolean IsAvailable(@RequestBody CreateReservationDTO res){
        return reservationService.isAvailable(res);
    }


}
