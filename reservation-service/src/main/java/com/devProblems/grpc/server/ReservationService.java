package com.devProblems.grpc.server;



import com.devProblems.*;
import com.devProblems.grpc.server.model.Reservation;
import com.devProblems.grpc.server.repo.ReservationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@GrpcService
public class ReservationService extends ReservationServiceGrpc.ReservationServiceImplBase {
    @GrpcClient("grpc-user-service")
    BookAuthorServiceGrpc.BookAuthorServiceBlockingStub synchronousClient;
    @Autowired
    ReservationRepository repository;

    @Override
    public void updateReservation(UpdateReq request, StreamObserver<ReservationResp> responseObserver) {
        Reservation reservation=repository.findById(Long.valueOf(request.getId())).get();
        reservation.setAccommodationId(request.getAccommodationId());
        reservation.setStartTime(LocalDate.parse(request.getStartDate()));
        reservation.setEndTime(LocalDate.parse(request.getEndDate()));
        reservation.setStatus(request.getStatus());
        reservation.setNumberOfPeople(request.getNumberOfGuests());
        repository.save(reservation);
        responseObserver.onNext(  ReservationResp.newBuilder()
                .setStartDate(reservation.getStartTime().toString())
                .setEndDate(reservation.getEndTime().toString())
                .setStatus(reservation.getStatus())
                .setAccommodationId(reservation.getAccommodationId()).
                build());
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(ReservationReq request, StreamObserver<ReservationResp> responseObserver) {
        Reservation res=new Reservation(request);
        repository.save(res);
        responseObserver.onNext(
                ReservationResp.newBuilder()
                        .setStartDate(res.getStartTime().toString())
                        .setEndDate(res.getEndTime().toString())
                        .setStatus(res.getStatus())
                        .setAccommodationId(res.getAccommodationId()).
                        build())
        ;
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(Delete request, StreamObserver<isAvailable> responseObserver) {
        Reservation reservation;

        try{reservation=repository.findById(Long.valueOf(request.getId())).get();}
        catch (Exception e ){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        if(reservation.getStatus()!=ReservationStatus.RESERVATION_STATUS_PENDING){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        reservation.setStatus(ReservationStatus.RESERVATION_STATUS_DELETED);
        repository.save(reservation);
        //repository.deleteById(request.getId());
        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();    
    }
    @Override
    public void checkAvailability(ReservationReq request, StreamObserver<isAvailable> responseObserver) {
        Author aut=synchronousClient.getAuthor(Author.newBuilder().setAuthorId(1).build());
        Boolean ret;
        if(aut!=null)
            ret=true;
        else
            ret=false;
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(ret).build());

        responseObserver.onCompleted();

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
                     .setAccommodationId(res.getAccommodationId())
                     .setNumberOfGuests(res.getNumberOfPeople())
                     .setStatus(res.getStatus())
                    .build());
        }
        return converted;
    }

}
