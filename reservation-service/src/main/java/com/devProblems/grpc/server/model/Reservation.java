package com.devProblems.grpc.server.model;
//import jakarta.pesistance.Entity;
import lombok.*;

import javax.persistence.*;

import javax.persistence.Entity;
import java.time.LocalDate;

import  com.devProblems.ReservationStatus;
@Data
@Entity
@Table(name="reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
     Long id;

     @Column
     private LocalDate startTime;
     @Column
     private LocalDate endTime;
     @Column
     private Integer numberOfPeople;
     @Column
     private Integer accommodation_id;
     @Column
     private ReservationStatus status;


}
