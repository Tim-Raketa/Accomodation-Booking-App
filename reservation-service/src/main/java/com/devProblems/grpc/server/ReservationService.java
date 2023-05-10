package com.devProblems.grpc.server;



import com.devProblems.*;
import com.devProblems.grpc.server.model.Reservation;
import com.devProblems.grpc.server.repo.ReservationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@GrpcService
public class ReservationService extends ReservationServiceGrpc.ReservationServiceImplBase {
    @Autowired
    ReservationRepository repository;

    @Override
    public void createReservation(ReservationReq request, StreamObserver<ReservationResp> responseObserver) {
        super.createReservation(request, responseObserver);
    }

    @Override
    public void checkAvailability(ReservationReq request, StreamObserver<isAvailable> responseObserver) {
        super.checkAvailability(request, responseObserver);
    }

    @Override
    public void getAllReservations(ListOfReservationResp request, StreamObserver<com.devProblems.ListOfReservationResp> responseObserver) {
        ListOfReservationResp moc=ListOfReservationResp.newBuilder().
                addAllReservations(convert(repository.findAll())).build();
        responseObserver.onNext(moc);
        responseObserver.onCompleted();
    }


    public List<ReservationResp> convert(List<Reservation> reservations) {
        List<ReservationResp> converted = new ArrayList<>();
        for (Reservation res : reservations) {

            converted.add(ReservationResp.newBuilder()
                    .setEndDate(res.getEndTime().toString())
                    .setStartDate(res.getStartTime().toString())
                     .setAccommodationId(res.getAccommodation_id())
                     .setNumberOfGuests(res.getNumberOfPeople())
                     .setStatus(res.getStatus())
                    .build());
        }
        return converted;
    }

}
