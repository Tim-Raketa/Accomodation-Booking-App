package com.devProblems.grpc.server.model;

import com.devProblems.AccommodationReq;
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
    @Column
    private String hostId;

    //private slike

    public Accommodation(com.devProblems.AccommodationReq req){
        this.name = req.getName();
        this.location = req.getLocation();
        this.perks = req.getPerks();
        this.minGuests = req.getMinGuests();
        this.maxGuests = req.getMaxGuests();
        this.hostId = req.getHostId();
    }

}
