package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.AccommodationDTO;
import com.devProblems.grpc.client.DTO.RentableIntervalDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationClientService {

    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousAccommodation;

    public AccommodationDTO createAccommodation(AccommodationDTO accommodation){
        AccommodationResp response = synchronousAccommodation.createAccommodation(
                AccommodationReq.newBuilder()
                        .setName(accommodation.getName())
                        .setLocation(accommodation.getLocation())
                        .setPerks(accommodation.getPerks())
                        .setMinGuests(accommodation.getMinGuests())
                        .setMaxGuests(accommodation.getMaxGuests())
                        .build()
        );
        return new AccommodationDTO(response);
    }

    public RentableIntervalDTO createRentableInterval(RentableIntervalDTO rentableInterval){
        RentableIntervalResp response = synchronousAccommodation.createRentableInterval(
                RentableIntervalReq.newBuilder()
                        .setAccommodationId(rentableInterval.getAccommodationId())
                        .setStartTime(rentableInterval.getStartTime().toString())
                        .setEndTime(rentableInterval.getEndTime().toString())
                        .setPriceOfAccommodation(rentableInterval.getPriceOfAccommodation())
                        .setPricePerGuest(rentableInterval.getPricePerGuest())
                        .setAutomaticAcceptance(rentableInterval.getAutomaticAcceptance())
                        .build()
        );
        return new RentableIntervalDTO(response);
    }

    public RentableIntervalDTO updateRentableInterval(RentableIntervalDTO rentableInterval){
        RentableIntervalResp response = synchronousAccommodation.updateRentableInterval(
                UpdateRentableIntervalReq.newBuilder()
                        .setId(rentableInterval.getId())
                        .setAccommodationId(rentableInterval.getAccommodationId())
                        .setStartTime(rentableInterval.getStartTime().toString())
                        .setEndTime(rentableInterval.getEndTime().toString())
                        .setPriceOfAccommodation(rentableInterval.getPriceOfAccommodation())
                        .setPricePerGuest(rentableInterval.getPricePerGuest())
                        .setAutomaticAcceptance(rentableInterval.getAutomaticAcceptance())
                        .build()
        );
        return new RentableIntervalDTO(response);
    }

    public List<AccommodationDTO> getAllAccommodations(){
        ListOfAccommodationResp emptyRequest = ListOfAccommodationResp.newBuilder().build();
        ListOfAccommodationResp response = synchronousAccommodation.getAllAccommodations(emptyRequest);
        List<AccommodationResp> resp = response.getAccommodationsList();
        return convert(resp);
    }

    public List<AccommodationDTO> convert(List<AccommodationResp> accommodations){
        List<AccommodationDTO> newList = new ArrayList<>();
        for(AccommodationResp resp : accommodations){
            newList.add(new AccommodationDTO(resp));
        }
        return newList;
    }

    public List<RentableIntervalDTO> getRentableIntervalsByAccommodationId(Long accommodationId){
        AccommodationIdReq request = AccommodationIdReq.newBuilder().setId(accommodationId).build();
        ListOfRentableIntervalResp response = synchronousAccommodation.getRentableIntervalsByAccommodationId(request);
        List<RentableIntervalResp> resp = response.getRentableIntervalsList();
        return convertRentableIntervals(resp);
    }

    public List<RentableIntervalDTO> convertRentableIntervals(List<RentableIntervalResp> rentableIntervals) {
        List<RentableIntervalDTO> newList = new ArrayList<>();
        for(RentableIntervalResp resp: rentableIntervals){
            newList.add(new RentableIntervalDTO(resp));
        }
        return newList;
    }

}
