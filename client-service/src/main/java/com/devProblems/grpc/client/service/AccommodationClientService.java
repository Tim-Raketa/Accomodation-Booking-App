package com.devProblems.grpc.client.service;

import com.devProblems.AccommodationReq;
import com.devProblems.AccommodationResp;
import com.devProblems.AccommodationServiceGrpc;
import com.devProblems.grpc.client.DTO.AccommodationDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

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
}
