package com.devProblems.grpc.client.service;

import com.devProblems.grpc.client.DTO.CreateReservationDTO;
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
    public Boolean CancelReservation(Long id,String username) {
        isAvailable response= synchronousReservation.deleteReservation(
                Delete.newBuilder()
                        .setId(id)
                        .build());
        return response.getAvailable();

    }
    public Boolean DeleteReservation(Long id) {
        isAvailable response= synchronousReservation.deleteReservation(
                Delete.newBuilder()
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
}
