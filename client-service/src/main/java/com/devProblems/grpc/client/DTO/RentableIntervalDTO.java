package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentableIntervalDTO {
    private Long id;
    private Long accommodationId;
    private String startTime;
    private String endTime;
    private Float priceOfAccommodation;
    private Float pricePerGuest;
    private Boolean automaticAcceptance;

    public RentableIntervalDTO(com.devProblems.RentableIntervalResp rentableInterval){
        this.id = rentableInterval.getId();
        this.accommodationId = rentableInterval.getAccommodationId();
        this.startTime = rentableInterval.getStartTime();
        this.endTime = rentableInterval.getEndTime();
        this.priceOfAccommodation = rentableInterval.getPriceOfAccommodation();
        this.pricePerGuest = rentableInterval.getPricePerGuest();
        this.automaticAcceptance = rentableInterval.getAutomaticAcceptance();
    }
}
