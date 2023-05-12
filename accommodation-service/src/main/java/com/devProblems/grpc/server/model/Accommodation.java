package com.devProblems.grpc.server.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name="accommodation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String name;
    @Column
    private String location;
    @Column
    private String perks;
    @Column
    private Integer minGuests;
    @Column
    private Integer maxGuests;

    //private slike



}
