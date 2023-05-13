package com.devProblems.grpc.server.model;
//import jakarta.pesistance.Entity;
import com.devProblems.ReservationReq;
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
     private Integer userId;
     @Column
     private Integer accommodationId;
     @Column
     private ReservationStatus status;

     public Reservation(ReservationReq req){
          this.startTime=LocalDate.parse(req.getStartDate());
          this.endTime=LocalDate.parse(req.getEndDate());
          this.numberOfPeople=req.getNumberOfGuests();
          this.accommodationId=req.getAccommodationId();
          this.status=ReservationStatus.RESERVATION_STATUS_PENDING;
     }

}
