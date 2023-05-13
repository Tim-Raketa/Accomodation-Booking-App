package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationDTO {

    Long id;
    String name;
    String location;
    String perks;
    Integer minGuests;
    Integer maxGuests;

    public AccommodationDTO(com.devProblems.AccommodationResp accommodation){
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.location = accommodation.getLocation();
        this.perks = accommodation.getPerks();
        this.minGuests = accommodation.getMinGuests();
        this.maxGuests = accommodation.getMaxGuests();
    }
}
