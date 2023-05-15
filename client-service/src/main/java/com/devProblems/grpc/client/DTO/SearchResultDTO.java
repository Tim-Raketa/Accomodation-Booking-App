package com.devProblems.grpc.client.DTO;

import com.devProblems.SearchResp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResultDTO {
    private Long accommodationId;
    private String name;
    private String location;
    private String perks;
    private Float totalPrice;
    private Float priceOfAccommodation;
    private Float pricePerGuest;
    private Integer numberOfGuests;
    private Integer minGuests;
    private Integer maxGuests;


    public SearchResultDTO(SearchResp response) {
        this.accommodationId = response.getAccommodationId();
        this.name = response.getName();
        this.location = response.getLocation();
        this.perks = response.getPerks();
        this.totalPrice = Float.valueOf(response.getTotalPrice());
        this.priceOfAccommodation = Float.valueOf(response.getPriceOfAccommodation());
        this.pricePerGuest = Float.valueOf(response.getPricePerGuest());
        this.numberOfGuests= response.getNumberOfGuests();
        this.minGuests=response.getMinGuests();
        this.maxGuests=response.getMaxGuests();
    }

}
