package com.devProblems.grpc.client.service;

import com.devProblems.AccommodationReq;
import com.devProblems.AccommodationResp;
import com.devProblems.AccommodationServiceGrpc;
import com.devProblems.ListOfAccommodationResp;
import com.devProblems.grpc.client.DTO.AccommodationDTO;
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
}
