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
    String startDate;
    String endDate;
    Integer numberOfGuests;
    Integer accommodationId;
    com.devProblems.ReservationStatus status;
    Long id;
    String username;

    public ReservationDTO(String start_date, String end_date, Integer numberOfGuests, Integer accommodation_id, ReservationStatus status) {
        this.startDate = start_date;
        this.endDate = end_date;
        this.numberOfGuests = numberOfGuests;
        this.accommodationId = accommodation_id;
        this.status = status;
    }

    public ReservationDTO(com.devProblems.ReservationResp reservation){
        this.startDate =reservation.getStartDate();
        this.endDate =reservation.getEndDate();
        this.numberOfGuests=reservation.getNumberOfGuests();
        this.accommodationId =reservation.getAccommodationId();
        this.status=reservation.getStatus();
        this.username=reservation.getUsername();
        this.id=reservation.getId();
    }

}
