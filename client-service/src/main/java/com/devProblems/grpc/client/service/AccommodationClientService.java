package com.devProblems.grpc.client.service;

import com.devProblems.*;
import com.devProblems.grpc.client.DTO.AccommodationDTO;
import com.devProblems.grpc.client.DTO.RentableIntervalDTO;
import com.devProblems.grpc.client.DTO.SearchRequestDTO;
import com.devProblems.grpc.client.DTO.SearchResultDTO;
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
                        .setHostId(accommodation.getHostId())
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

    public List<AccommodationDTO> getAccommodationsByHostId(String hostId){
        HostIdReq request = HostIdReq.newBuilder().setHostId(hostId).build();
        ListOfAccommodationResp response = synchronousAccommodation.getAccommodationsByHostId(request);
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

    public AccommodationDTO getById(Long id) {
        AccommodationIdReq request = AccommodationIdReq.newBuilder().setId(id).build();
        AccommodationResp response = synchronousAccommodation.getById(request);
        return new AccommodationDTO(response);
    }


    public List<SearchResultDTO> search(SearchRequestDTO searchReq) {
        SearchReq request=SearchReq.newBuilder().setStartDate(searchReq.getStartDate()).setEndDate(searchReq.getEndDate())
                .setLocation(searchReq.getLocation()).setNumberOfGuests(searchReq.getNumberOfGuests()).build();
        ListOfSearchResp response=synchronousAccommodation.search(request);
        List<SearchResp> list=response.getResponsesList();
        return convertForSearch(list);
    }
    public List<SearchResultDTO> convertForSearch(List<SearchResp> requests){
        List<SearchResultDTO> results=new ArrayList<>();
        for (SearchResp req:requests
             ) {
            results.add(new SearchResultDTO(req));
        }
        return results;
    }


    public RentableIntervalDTO getRentableIntervalById(Long id){
        RentableIntervalIdReq request = RentableIntervalIdReq.newBuilder().setId(id).build();
        RentableIntervalResp response = synchronousAccommodation.getRentableIntervalById(request);
        return new RentableIntervalDTO(response);
    }

}
