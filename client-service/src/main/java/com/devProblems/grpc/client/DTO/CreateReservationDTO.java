package com.devProblems.grpc.client.DTO;

import com.devProblems.ReservationReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationDTO {
    String start_date ;
    String end_date;
    Integer numberOfGuests;
    Integer accommodation_id;

}
