package com.devProblems.grpc.client.controller;

import com.devProblems.grpc.client.DTO.FlightDTO;
import com.devProblems.grpc.client.DTO.FlightSearchDTO;
import com.devProblems.grpc.client.DTO.NewTicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/flights", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class FlightController {


    @PostMapping(value="/search",consumes = "application/json")
    public List<FlightDTO> search(@RequestBody FlightSearchDTO details){
        RestTemplate restTemplate=new RestTemplate();
        FlightDTO[] flights=restTemplate.postForObject("http://localhost:8082/Flights/search",details,FlightDTO[].class);
        return Arrays.stream(flights).toList();
    }
    @GetMapping(value="/userExist/{email}")
    public Boolean exists(@PathVariable String email){
        RestTemplate restTemplate=new RestTemplate();
        Boolean exists=restTemplate.getForObject("http://localhost:8082/users/exists/"+email,Boolean.class);
        return exists;
    }
    @PostMapping(value = "/buy/ticket", consumes = "application/json")
    public Boolean NewTicket(@RequestBody NewTicketDTO ticket) {
        RestTemplate restTemplate=new RestTemplate();
        Boolean success=restTemplate.postForObject("http://localhost:8082/Tickets/buy/ticket/unprotected",ticket,Boolean.class);
        return success;
    }

}
