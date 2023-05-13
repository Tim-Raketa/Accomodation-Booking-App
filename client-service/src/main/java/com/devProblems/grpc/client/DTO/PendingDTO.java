package com.devProblems.grpc.client.DTO;

import com.devProblems.Pending;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PendingDTO {
    String startDate;
    String endDate;
    Integer numberOfGuests;
    Integer getCancelCount;

    Long accommodationId;
    Long id;
    String username;

    public PendingDTO(Pending pending) {
        this.startDate = pending.getStartDate();
        this.endDate = pending.getEndDate();
        this.numberOfGuests = pending.getNumberOfGuests();
        this.accommodationId = pending.getAccommodationId();
        this.id = pending.getId();
        this.username = pending.getUsername();
        this.getCancelCount=pending.getCancelCount();
    }
}
