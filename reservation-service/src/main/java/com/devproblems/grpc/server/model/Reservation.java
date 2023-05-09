package com.devproblems.grpc.server.model;
//import jakarta.pesistance.Entity;
import lombok.*;

import javax.persistence.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import com.devProblems.ReservationStatus;

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
     private LocalDateTime startTime;
     @Column
     private LocalDateTime endTime;
     @Column
     private Integer numberOfPeople;
     @Column
     private Integer accommodation_id;
     @Column
     private ReservationStatus status;

}
