package com.devProblems.grpc.server.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="rentableInterval")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentableInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private Long accommodationId;
    @Column
    private LocalDate startTime;
    @Column
    private LocalDate endTime;
    @Column
    private Float priceOfAccommodation;
    @Column
    private Float pricePerGuest;
    @Column
    private Boolean automaticAcceptance;



}
