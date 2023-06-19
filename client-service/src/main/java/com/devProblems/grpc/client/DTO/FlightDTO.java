package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDTO {
    private Integer id;
    private String dateTime;
    private String startingLocation;
    private String destination;
    private Float pricePerPerson;
    private Float totalPrice;



}
