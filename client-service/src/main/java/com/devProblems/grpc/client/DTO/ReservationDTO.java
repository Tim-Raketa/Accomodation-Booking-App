package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    String start_date ;
    String end_date;
    Integer numberOfGuests;
    Integer accommodation_id;
    com.devProblems.ReservationStatus status;

    public ReservationDTO(com.devProblems.ReservationResp reservation){
        this.start_date=reservation.getStartDate();
        this.end_date=reservation.getEndDate();
        this.numberOfGuests=reservation.getNumberOfGuests();
        this.accommodation_id=reservation.getAccommodationId();
        this.status=reservation.getStatus();
    }

}
