package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequestDTO {
    private String Location;
    private Integer numberOfGuests;
    private String startDate;
    private String endDate;

}
