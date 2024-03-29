package com.devProblems.grpc.client.service;

import com.devProblems.grpc.client.DTO.AccommodationDTO;
import com.devProblems.grpc.client.DTO.CreateReservationDTO;
import com.devProblems.grpc.client.DTO.PendingDTO;
import com.devProblems.grpc.client.DTO.ReservationDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import com.devProblems.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @GrpcClient("grpc-reservation-service")
    ReservationServiceGrpc.ReservationServiceBlockingStub synchronousReservation;
    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousAccommodation;

    public List<ReservationDTO> getAllReservations(){
        ListOfReservationResp emptyRequest= ListOfReservationResp.newBuilder().build();
        ListOfReservationResp response= synchronousReservation.getAllReservations(emptyRequest);
        List<ReservationResp> resp=response.getReservationsList();
        return convert(resp);
    }

    public List<ReservationDTO> convert(List<ReservationResp> xyz){
        List<ReservationDTO> newList=new ArrayList<>();
        for (ReservationResp resp: xyz) {
            newList.add(new ReservationDTO(resp));
        }
        return newList;
    }

    public ReservationDTO addReservations(CreateReservationDTO res) {
        ReservationResp response= synchronousReservation.createReservation(
                ReservationReq.newBuilder()
                        .setAccommodationId((int)res.getAccommodationId())
                        .setStartDate(res.getStartDate().toString())
                        .setEndDate(res.getEndDate().toString())
                        .setNumberOfGuests(res.getNumberOfGuests())
                        .setUsername(res.getUsername())
                        .build());

        return new ReservationDTO(response);
    }

    public ReservationDTO updateReservation(ReservationDTO res) {
        ReservationResp response= synchronousReservation.updateReservation(
                UpdateReq.newBuilder()
                        .setAccommodationId(res.getAccommodationId())
                        .setStartDate(res.getStartDate())
                        .setEndDate(res.getEndDate())
                        .setNumberOfGuests(res.getNumberOfGuests())
                        .setStatus(res.getStatus())
                        .setId(res.getId())
                        .build());
        return new ReservationDTO(response);

    }
    public Boolean CancelReservation(Long id) {
        isAvailable response= synchronousReservation.cancelReservation(
                AccommodationId.newBuilder()
                        .setId(id)
                        .build());
        return response.getAvailable();

    }
    public Boolean AcceptReservation(Long id) {
        isAvailable response= synchronousReservation.acceptReservation(
                AccommodationId.newBuilder()
                        .setId(id)
                        .build());
        return response.getAvailable();

    }
    public Boolean DeleteReservation(Long id) {
        isAvailable response= synchronousReservation.deleteReservation(
                AccommodationId.newBuilder()
                        .setId(id)
                        .build());
        return response.getAvailable();

    }
    public Boolean DenyReservation(Long id) {
        isAvailable response= synchronousReservation.declineReservation(
                AccommodationId.newBuilder()
                        .setId(id)
                        .build());
        return response.getAvailable();
    }
    public Boolean isAvailable(CreateReservationDTO res) {
        isAvailable response= synchronousReservation.checkAvailability(
                ReservationReq.newBuilder()
                        .setAccommodationId(res.getAccommodationId())
                        .setStartDate(res.getStartDate().toString())
                        .setEndDate(res.getEndDate().toString())
                        .setNumberOfGuests(res.getNumberOfGuests())
                        .build());

        return response.getAvailable();
    }

    public List<PendingDTO> getAllPending(Long accommodationId) {
        AccommodationId Request= AccommodationId.newBuilder().setId(accommodationId).build();
        allPending response= synchronousReservation.showAllPendingReservations(Request);
        List<Pending> resp=response.getPendingList();
        return convertPending(resp);
    }
    public List<PendingDTO> getAllAccepted(Long accommodationId) {
        AccommodationId Request = AccommodationId.newBuilder().setId(accommodationId).build();
        allPending response= synchronousReservation.showAllAcceptedReservationsAccommodation(Request);
        List<Pending> resp = response.getPendingList();
        return convertPending(resp);
    }

    public List<PendingDTO> convertPending(List<Pending> xyz){
        List<PendingDTO> newList=new ArrayList<>();
        for (Pending resp: xyz) {
            newList.add(new PendingDTO(resp));
        }
        return newList;
    }

    public List<PendingDTO> getAllAccepted(String username) {
        UsernameReq Request= UsernameReq.newBuilder().setUsername(username).build();
        allPending response= synchronousReservation.showAllAcceptedReservations(Request);
        List<Pending> resp=response.getPendingList();
        return convertPending(resp);
    }

    public List<PendingDTO> getAllPending(String username) {
        UsernameReq Request= UsernameReq.newBuilder().setUsername(username).build();
        allPending response= synchronousReservation.getAllPendingReservationsUser(Request);
        List<Pending> resp=response.getPendingList();
        return convertPending(resp);
    }


    public List<AccommodationDTO> hasVisited(String username) {
        HasVisitedReq req = HasVisitedReq.newBuilder().setUsername(username).build();
        HasVisitedResp response = synchronousReservation.hasVisited(req);
        var visited=convertAcc(response.getAccommodationIdList().stream().map(id->synchronousAccommodation.getById(AccommodationIdReq.newBuilder().setId(id).build())).toList());
        return visited;
    }

    public List<AccommodationDTO> convertAcc(List<AccommodationResp> accommodations){
        List<AccommodationDTO> newList = new ArrayList<>();
        for(AccommodationResp resp : accommodations){
            newList.add(new AccommodationDTO(resp));
        }
        return newList;
    }

    public ReservationDTO getById(Long id) {
        return new ReservationDTO(synchronousReservation.getById(AccommodationId.newBuilder().setId(id).build()));
    }
}
