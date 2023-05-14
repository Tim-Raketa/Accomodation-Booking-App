package com.devProblems.grpc.server;

import com.devProblems.*;
import com.devProblems.grpc.server.model.Accommodation;
import com.devProblems.grpc.server.model.RentableInterval;
import com.devProblems.grpc.server.repo.AccommodationRepository;
import com.devProblems.grpc.server.repo.RentableIntervalRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class AccommodationServerService extends AccommodationServiceGrpc.AccommodationServiceImplBase {

    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    RentableIntervalRepository rentableIntervalRepository;

    @Override
    public void isAutomatic(AccommodationAvailable request, StreamObserver<Automatic> responseObserver) {
        RentableInterval interval=
                rentableIntervalRepository.findAll().stream().filter(interval1-> interval1.getAccommodationId()==request.getId()
                        &&
                        (interval1.getStartTime()
                                .isBefore
                                        (LocalDate.parse(request.getStartDate()))
                                &&(interval1.getEndTime() .isAfter(LocalDate.parse(request.getEndDate()))
                        ))
                ).findFirst().get();
        responseObserver.onNext(Automatic.newBuilder().setAuto(interval.getAutomaticAcceptance()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void createAccommodation(AccommodationReq request, StreamObserver<AccommodationResp> responseObserver) {
        Accommodation accommodation = new Accommodation(request);
        accommodationRepository.save(accommodation);
        responseObserver.onNext(
                AccommodationResp.newBuilder()
                        .setId(accommodation.getId())
                        .setName(accommodation.getName())
                        .setLocation(accommodation.getLocation())
                        .setPerks(accommodation.getPerks())
                        .setMinGuests(accommodation.getMinGuests())
                        .setMaxGuests(accommodation.getMaxGuests())
                        .build()
        );
        responseObserver.onCompleted();
    }

    /*
    List<Reservation> reservations= repository.findAll().stream().filter(res->res.getAccommodationId()==reservation.getAccommodationId())
                .filter(res -> res.getStatus()==ReservationStatus.RESERVATION_STATUS_PENDING)
                .filter(res-> !(res.getEndTime().isBefore(reservation.getStartTime()) || res.getStartTime().isAfter(reservation.getStartTime()) )).toList();
    * */

    @Override
    public void createRentableInterval(RentableIntervalReq request, StreamObserver<RentableIntervalResp> responseObserver) {
        RentableInterval interval = new RentableInterval(request);
        List<RentableInterval> intervals = rentableIntervalRepository.findAll().stream().filter(rentableInterval->rentableInterval.getAccommodationId() == interval.getAccommodationId())
                .filter(rentableInterval-> !(rentableInterval.getEndTime().isBefore(interval.getStartTime()) || rentableInterval.getStartTime().isAfter(interval.getStartTime()) )).toList();
        if(!intervals.isEmpty()){
            responseObserver.onNext(RentableIntervalResp.newBuilder().build());
        } else {
            rentableIntervalRepository.save(interval);
            responseObserver.onNext(
                    RentableIntervalResp.newBuilder()
                            .setId(interval.getId())
                            .setAccommodationId(interval.getAccommodationId())
                            .setStartTime(interval.getStartTime().toString())
                            .setEndTime(interval.getEndTime().toString())
                            .setPriceOfAccommodation(interval.getPriceOfAccommodation())
                            .setPricePerGuest(interval.getPricePerGuest())
                            .setAutomaticAcceptance(interval.getAutomaticAcceptance())
                            .build()
            );
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateRentableInterval(UpdateRentableIntervalReq request, StreamObserver<RentableIntervalResp> responseObserver) {
        RentableInterval rentableInterval = rentableIntervalRepository.findById(Long.valueOf(request.getId())).get();
        rentableInterval.setAccommodationId(request.getAccommodationId());
        rentableInterval.setStartTime(LocalDate.parse(request.getStartTime()));
        rentableInterval.setEndTime(LocalDate.parse(request.getEndTime()));
        rentableInterval.setPriceOfAccommodation(request.getPriceOfAccommodation());
        rentableInterval.setPricePerGuest(request.getPricePerGuest());
        rentableInterval.setAutomaticAcceptance(request.getAutomaticAcceptance());
        rentableIntervalRepository.save(rentableInterval);
        responseObserver.onNext(
                RentableIntervalResp.newBuilder()
                        .setId(rentableInterval.getId())
                        .setAccommodationId(rentableInterval.getAccommodationId())
                        .setStartTime(rentableInterval.getStartTime().toString())
                        .setEndTime(rentableInterval.getEndTime().toString())
                        .setPriceOfAccommodation(rentableInterval.getPriceOfAccommodation())
                        .setPricePerGuest(rentableInterval.getPricePerGuest())
                        .setAutomaticAcceptance(rentableInterval.getAutomaticAcceptance())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getRentableIntervalsByAccommodationId(AccommodationIdReq request, StreamObserver<ListOfRentableIntervalResp> responseObserver) {
        List<RentableInterval> rentableIntervals = rentableIntervalRepository.findAll().stream().filter(rentableInterval -> rentableInterval.getAccommodationId()==request.getId())
                .toList();
        responseObserver.onNext(ListOfRentableIntervalResp.newBuilder().addAllRentableIntervals(convertRentableIntervals(rentableIntervals)).build());
        responseObserver.onCompleted();
    }

    public List<RentableIntervalResp> convertRentableIntervals(List<RentableInterval> rentableIntervals) {
        List<RentableIntervalResp> converted = new ArrayList<>();
        for(RentableInterval rentableInterval: rentableIntervals){
            converted.add(RentableIntervalResp.newBuilder()
                    .setId(rentableInterval.getId())
                    .setAccommodationId(rentableInterval.getAccommodationId())
                    .setStartTime(rentableInterval.getStartTime().toString())
                    .setEndTime(rentableInterval.getEndTime().toString())
                    .setPriceOfAccommodation(rentableInterval.getPriceOfAccommodation())
                    .setPricePerGuest(rentableInterval.getPricePerGuest())
                    .setAutomaticAcceptance(rentableInterval.getAutomaticAcceptance())
                    .build());
        }
        return converted;
    }

    @Override
    public void getAllAccommodations(ListOfAccommodationResp request, StreamObserver<ListOfAccommodationResp> responseObserver) {
        ListOfAccommodationResp accommodations = ListOfAccommodationResp.newBuilder()
                .addAllAccommodations(convert(accommodationRepository.findAll())).build();
        responseObserver.onNext(accommodations);
        responseObserver.onCompleted();
    }

    public List<AccommodationResp> convert(List<Accommodation> accommodations){
        List<AccommodationResp> converted = new ArrayList<>();
        for(Accommodation accommodation : accommodations){
            converted.add(AccommodationResp.newBuilder()
                    .setId(accommodation.getId())
                    .setName(accommodation.getName())
                    .setLocation(accommodation.getLocation())
                    .setPerks(accommodation.getPerks())
                    .setMinGuests(accommodation.getMinGuests())
                    .setMaxGuests(accommodation.getMaxGuests())
                    .build());
        }
        return converted;
    }
}
