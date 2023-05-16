package com.devProblems.grpc.server;



import com.devProblems.*;
import com.devProblems.grpc.server.model.Reservation;
import com.devProblems.grpc.server.repo.ReservationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@GrpcService
public class ReservationService extends ReservationServiceGrpc.ReservationServiceImplBase {

    @GrpcClient("grpc-user-service")
    UserServiceGrpc.UserServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousClientAccommodation;
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
                .setAccommodationId(reservation.getAccommodationId())
                        .setNumberOfGuests(reservation.getNumberOfPeople()).
                setId(reservation.getId())
                .setUsername(reservation.getUsername()).
                build());
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(ReservationReq request, StreamObserver<ReservationResp> responseObserver) {
        Reservation res=new Reservation(request);
        Automatic auto=synchronousClientAccommodation.isAutomatic(AccommodationAvailable.newBuilder()
                .setId(res.getAccommodationId())
                .setStartDate(res.getStartTime().toString())
                .setEndDate(res.getEndTime().toString())
                .build());
        if(auto.getAuto())
            res.setStatus(ReservationStatus.RESERVATION_STATUS_ACCEPTED);

        res=repository.save(res);
        responseObserver.onNext(
                ReservationResp.newBuilder()
                        .setStartDate(res.getStartTime().toString())
                        .setEndDate(res.getEndTime().toString())
                        .setStatus(res.getStatus())
                        .setAccommodationId(res.getAccommodationId())
                        .setNumberOfGuests(res.getNumberOfPeople())
                        .setUsername(res.getUsername()).
                        setId(res.getId()).
                        build())
        ;
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(AccommodationId request, StreamObserver<isAvailable> responseObserver) {
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
        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();    
    }

    @Override
    public void declineReservation(AccommodationId request, StreamObserver<isAvailable> responseObserver) {
        Reservation reservation;

        try{reservation=repository.findById(Long.valueOf(request.getId())).get();}
        catch (Exception e ){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        if(reservation.getStatus()!=ReservationStatus.RESERVATION_STATUS_PENDING
                || reservation.getStartTime().isBefore(LocalDate.now().plusDays(1))){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        reservation.setStatus(ReservationStatus.RESERVATION_STATUS_DECLINED);
        repository.save(reservation);

        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();
    }



    @Override
    public void cancelReservation(AccommodationId request, StreamObserver<isAvailable> responseObserver) {
        Reservation reservation;

        try{reservation=repository.findById(Long.valueOf(request.getId())).get();}
        catch (Exception e ){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        if(reservation.getStatus()!=ReservationStatus.RESERVATION_STATUS_ACCEPTED
                || reservation.getStartTime().isBefore(LocalDate.now().plusDays(1))){
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
            responseObserver.onCompleted();
            return;
        }
        reservation.setStatus(ReservationStatus.RESERVATION_STATUS_CANCELED);
        repository.save(reservation);
        synchronousClient.increaseCancelCount(UsernameMsg.newBuilder().setUsername(reservation.getUsername()).build());

        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void checkAvailability(ReservationReq request, StreamObserver<isAvailable> responseObserver) {
        // Author aut=synchronousClient.getAuthor(Author.newBuilder().setAuthorId(1).build());
        Boolean ret;
        // if(aut!=null)
            ret=true;
        //else
          //  ret=false;
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

    @Override
    public void acceptReservation(AccommodationId request, StreamObserver<isAvailable> responseObserver) {
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
        reservation.setStatus(ReservationStatus.RESERVATION_STATUS_ACCEPTED);
        repository.save(reservation);
        List<Reservation> reservations= repository.findAll().stream().filter(res->res.getAccommodationId()==reservation.getAccommodationId())
                .filter(res -> res.getStatus()==ReservationStatus.RESERVATION_STATUS_PENDING)
                .filter(res-> !(res.getEndTime().isBefore(reservation.getStartTime()) || res.getStartTime().isAfter(reservation.getStartTime()) )).toList();
        reservations.forEach(res->res.setStatus(ReservationStatus.RESERVATION_STATUS_DELETED));
        repository.saveAll(reservations);

        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();
    }
    @Override
    public void showAllAcceptedReservations(UsernameReq request, StreamObserver<allPending> responseObserver) {
        List<Reservation> reservations=repository.findAll().stream().filter(reservation->reservation.getUsername().equalsIgnoreCase(request.getUsername()))
                .filter(reservation -> reservation.getStatus()==ReservationStatus.RESERVATION_STATUS_ACCEPTED)
                .filter(reservation -> reservation.getStartTime().isAfter(LocalDate.now()))
                .toList();

        responseObserver.onNext(allPending.newBuilder().addAllPending(convertToPending(reservations)).build());
        responseObserver.onCompleted();
    }
    @Override
    public void isIntervalFree(isIntervalFreMsg request, StreamObserver<isAvailable> responseObserver) {
        List<Reservation> reservations= repository.findAll().stream().filter(res->res.getAccommodationId()==request.getAccommodationId())
                .filter(res -> res.getStatus()==ReservationStatus.RESERVATION_STATUS_ACCEPTED)
                .filter(res-> !(res.getEndTime().isBefore(LocalDate.parse(request.getStartDate())) || res.getStartTime().isAfter(LocalDate.parse(request.getEndDate())) )).
                toList();
        if(reservations.isEmpty())
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        else
            responseObserver.onNext(isAvailable.newBuilder().setAvailable(false).build());
        responseObserver.onCompleted();
    }

    @Override
    public void showAllAcceptedReservationsAccommodation(AccommodationId request, StreamObserver<allPending> responseObserver) {
        List<Reservation> reservations=repository.findAll().stream().filter(reservation->reservation.getAccommodationId()==request.getId())
                .filter(reservation -> reservation.getStatus()==ReservationStatus.RESERVATION_STATUS_ACCEPTED)
                .filter(reservation -> reservation.getStartTime().isAfter(LocalDate.now()))
                .toList();

        responseObserver.onNext(allPending.newBuilder().addAllPending(convertToPending(reservations)).build());
        responseObserver.onCompleted();
    }
    @Override
    public void showAllPendingReservations(AccommodationId request, StreamObserver<allPending> responseObserver) {
        List<Reservation> reservations=repository.findAll().stream().filter(reservation->reservation.getAccommodationId()==request.getId())
                .filter(reservation -> reservation.getStatus()==ReservationStatus.RESERVATION_STATUS_PENDING)
                .filter(reservation -> reservation.getStartTime().isAfter(LocalDate.now()))
                .toList();

        responseObserver.onNext(allPending.newBuilder().addAllPending(convertToPending(reservations)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllPendingReservationsUser(UsernameReq request, StreamObserver<allPending> responseObserver) {
        List<Reservation> reservations=repository.findAll().stream().filter(reservation->reservation.getUsername().equals(request.getUsername()))
                .filter(reservation -> reservation.getStatus()==ReservationStatus.RESERVATION_STATUS_PENDING)
                .filter(reservation -> reservation.getStartTime().isAfter(LocalDate.now()))
                .toList();
        responseObserver.onNext(allPending.newBuilder().addAllPending(convertToPending(reservations)).build());
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
                     .setUsername(res.getUsername())
                            .setId(res.getId())
                    .build());
        }
        return converted;
    }

    public List<Pending> convertToPending(List<Reservation> reservations) {
        List<Pending> converted = new ArrayList<>();
        for (Reservation res : reservations) {
            CancelCountMsg cancelCount=synchronousClient.getCancelCount(UsernameMsg.newBuilder().setUsername(res.getUsername()).build());
            converted.add(Pending.newBuilder()
                    .setEndDate(res.getEndTime().toString())
                    .setStartDate(res.getStartTime().toString())
                    .setAccommodationId(res.getAccommodationId())
                    .setNumberOfGuests(res.getNumberOfPeople())
                    .setUsername(res.getUsername())
                    .setCancelCount(cancelCount.getCancelCount())
                    .setId(res.getId())
                    .build());
        }
        return converted;

    }

    @Override
    public void deleteAllForAccommodation(AccommodationId request, StreamObserver<isAvailable> responseObserver) {
        List<Reservation> toDelete=repository.findAll().stream().filter(reservation -> reservation.getAccommodationId()==request.getId()).toList();
        toDelete.forEach(reservation -> reservation.setStatus(ReservationStatus.RESERVATION_STATUS_DELETED));
        repository.saveAll(toDelete);
        responseObserver.onNext(isAvailable.newBuilder().setAvailable(true).build());
        responseObserver.onCompleted();
    }
}
