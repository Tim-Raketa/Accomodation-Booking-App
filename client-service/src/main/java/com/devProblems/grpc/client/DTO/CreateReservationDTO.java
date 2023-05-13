package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationDTO {
    String startDate;
    String endDate;
    Integer numberOfGuests;
    Integer accommodationId;
    String username;

}
