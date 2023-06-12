package com.devProblems.grpc.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterRequestDTO {
    private String amenities;
    private Integer minGrade;
    private Integer maxGrade;
    private Boolean onlyHighlighted;
    private String Location;
    private Integer numberOfGuests;
    private String startDate;
    private String endDate;



}
