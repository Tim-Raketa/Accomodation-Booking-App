package com.devProblems.grpc.client.DTO;

import com.devProblems.ReservationStatus;
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
    Integer id;

    public ReservationDTO(String start_date, String end_date, Integer numberOfGuests, Integer accommodation_id, ReservationStatus status) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.numberOfGuests = numberOfGuests;
        this.accommodation_id = accommodation_id;
        this.status = status;
    }

    public ReservationDTO(com.devProblems.ReservationResp reservation){
        this.start_date=reservation.getStartDate();
        this.end_date=reservation.getEndDate();
        this.numberOfGuests=reservation.getNumberOfGuests();
        this.accommodation_id=reservation.getAccommodationId();
        this.status=reservation.getStatus();
    }

}
