package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewTicketDTO {
    private Integer flightId;
    private Integer numberOfPeople;
    private String email;
    private Integer id;


    public NewTicketDTO(Integer flightId, Integer numberOfPeople, String email) {
        this.flightId = flightId;
        this.numberOfPeople = numberOfPeople;
        this.email = email;
    }

}
