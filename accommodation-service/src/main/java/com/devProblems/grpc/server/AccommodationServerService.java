package com.devProblems.grpc.server;

import com.devProblems.*;
import com.devProblems.grpc.server.model.Accommodation;
import com.devProblems.grpc.server.model.RentableInterval;
import com.devProblems.grpc.server.repo.AccommodationRepository;
import com.devProblems.grpc.server.repo.RentableIntervalRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcService
public class AccommodationServerService extends AccommodationServiceGrpc.AccommodationServiceImplBase {

    @GrpcClient("grpc-reservation-service")
    ReservationServiceGrpc.ReservationServiceBlockingStub synchronousClient;

    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    RentableIntervalRepository rentableIntervalRepository;

    @Override
    public void isAutomatic(AccommodationAvailable request, StreamObserver<Automatic> responseObserver) {
        Optional<RentableInterval> interval=
                rentableIntervalRepository.findAll().stream().filter(interval1-> interval1.getAccommodationId()==request.getId()
                        &&
                        (interval1.getStartTime()
                                .isBefore
                                        (LocalDate.parse(request.getStartDate()))
                                &&(interval1.getEndTime() .isAfter(LocalDate.parse(request.getEndDate()))
                        ))
                ).findFirst();
        if(interval.isEmpty())
            responseObserver.onNext(Automatic.newBuilder().build());
        else
            responseObserver.onNext(Automatic.newBuilder().setAuto(interval.get().getAutomaticAcceptance()).build());
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
                        .setHostId(accommodation.getHostId())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void createRentableInterval(RentableIntervalReq request, StreamObserver<RentableIntervalResp> responseObserver) {
        RentableInterval interval = new RentableInterval(request);
        List<RentableInterval> intervals = rentableIntervalRepository.findAll().stream().filter(rentableInterval->rentableInterval.getAccommodationId() == interval.getAccommodationId())
                .filter(rentableInterval-> !(rentableInterval.getEndTime().isBefore(interval.getStartTime()) || rentableInterval.getStartTime().isAfter(interval.getEndTime()) )).toList();
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
        isAvailable isAvailable = synchronousClient.isIntervalFree(isIntervalFreMsg.newBuilder()
                .setAccommodationId(rentableInterval.getAccommodationId())
                .setStartDate(rentableInterval.getStartTime().toString())
                .setEndDate(rentableInterval.getEndTime().toString())
                .build());

        rentableInterval.setAccommodationId(request.getAccommodationId());
        rentableInterval.setStartTime(LocalDate.parse(request.getStartTime()));
        rentableInterval.setEndTime(LocalDate.parse(request.getEndTime()));
        rentableInterval.setPriceOfAccommodation(request.getPriceOfAccommodation());
        rentableInterval.setPricePerGuest(request.getPricePerGuest());
        rentableInterval.setAutomaticAcceptance(request.getAutomaticAcceptance());

        List<RentableInterval> intervals = rentableIntervalRepository.findAll().stream().filter(rI->rI.getAccommodationId() == rentableInterval.getAccommodationId() && rI.getId()!=rentableInterval.getId())
                .filter(rI-> !(rI.getEndTime().isBefore(rentableInterval.getStartTime()) || rI.getStartTime().isAfter(rentableInterval.getEndTime()) )).toList();



        if(intervals.isEmpty() && (isAvailable.getAvailable())){
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
        }
        else
        {
            responseObserver.onNext(RentableIntervalResp.newBuilder().build());
        }
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
                .addAllAccommodations(convert(accommodationRepository.findAll().stream().filter(accommodation -> accommodation.getDeleted()==false).toList())).build();
        responseObserver.onNext(accommodations);
        responseObserver.onCompleted();
    }

    @Override
    public void getAccommodationsByHostId(HostIdReq request, StreamObserver<ListOfAccommodationResp> responseObserver) {
        List<Accommodation> accommodations = accommodationRepository.findAll().stream().filter(accommodation -> accommodation.getHostId().equals(request.getHostId()) &&accommodation.getDeleted()==false).toList();
        responseObserver.onNext(ListOfAccommodationResp.newBuilder().addAllAccommodations(convert(accommodations)).build());
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
                    .setHostId(accommodation.getHostId())
                    .build());
        }
        return converted;
    }

    @Override
    public void getById(AccommodationIdReq request, StreamObserver<AccommodationResp> responseObserver) {
        Optional<Accommodation> accommodation=accommodationRepository.findById(request.getId());
        if(accommodation.isPresent() && accommodation.get().getDeleted()==false)
            responseObserver.onNext(
                    AccommodationResp.newBuilder()
                            .setId(accommodation.get().getId())
                            .setName(accommodation.get().getName())
                            .setLocation(accommodation.get().getLocation())
                            .setPerks(accommodation.get().getPerks())
                            .setMinGuests(accommodation.get().getMinGuests())
                            .setMaxGuests(accommodation.get().getMaxGuests())
                            .setHostId(accommodation.get().getHostId())
                            .build()
            );
         else  responseObserver.onNext(
                AccommodationResp.newBuilder().build());
         responseObserver.onCompleted();

    }

    @Override
    public void search(SearchReq request, StreamObserver<ListOfSearchResp> responseObserver) {
        List<SearchResp> responses=new ArrayList<>();
        //radi se search za sve osim termina
        List<Accommodation> accomodations=accommodationRepository.findAll().stream()
                .filter(accommodation -> accommodation.getLocation().toLowerCase().contains(request.getLocation().toLowerCase()) && accommodation.getDeleted()==false)
                .filter(accommodation -> accommodation.getMaxGuests()>=request.getNumberOfGuests()
                        && request.getNumberOfGuests()>=accommodation.getMinGuests())
                .toList()
                ;
        for (var acc:accomodations) {

            Optional<RentableInterval> Interval = rentableIntervalRepository.findAll().stream()
                    .filter(interval-> interval.getAccommodationId() == acc.getId())
                    .filter(interval-> interval.getStartTime().isBefore(LocalDate.parse(request.getStartDate()).plusDays(1))
                            && interval.getEndTime().isAfter(LocalDate.parse(request.getEndDate()).minusDays(1))
                    ).findFirst();

           if(Interval.isPresent()) {
               Boolean isAvailable = synchronousClient.isIntervalFree(isIntervalFreMsg.newBuilder()
                       .setAccommodationId(Interval.get().getAccommodationId())
                       .setStartDate(request.getStartDate())
                       .setEndDate(request.getEndDate())
                       .build()).getAvailable();
              Long days= ChronoUnit.DAYS.between( LocalDate.parse(request.getStartDate()),LocalDate.parse(request.getEndDate()));
               if (isAvailable)
                   responses.add(conv(acc,Interval.get().getPriceOfAccommodation(),Interval.get().getPricePerGuest()
                           ,request.getNumberOfGuests(),days));
           }
        }
        responseObserver.onNext(ListOfSearchResp.newBuilder().addAllResponses(responses).build());
        responseObserver.onCompleted();
    }
    public SearchResp conv (Accommodation accommodation,Float priceOfAccommodation,Float pricePerPerson
            ,Integer numberOfGuests,Long days){

            return    (SearchResp.newBuilder()
                        .setAccommodationId(accommodation.getId())
                        .setName(accommodation.getName())
                        .setLocation(accommodation.getLocation())
                        .setPerks(accommodation.getPerks())
                        .setPriceOfAccommodation(priceOfAccommodation.longValue())
                        .setPricePerGuest(pricePerPerson.longValue())
                        .setTotalPrice(days*pricePerPerson.longValue()*numberOfGuests + days*priceOfAccommodation.longValue())
                        .setNumberOfGuests(numberOfGuests)
                        .setMaxGuests(accommodation.getMaxGuests())
                        .setMinGuests(accommodation.getMinGuests())
                        .build());

    }

    @Override
    public void deleteAccommodation(AccommodationIdReq request, StreamObserver<Automatic> responseObserver) {
        Optional<Accommodation> deleted=accommodationRepository.findById(request.getId());
        if(deleted.isEmpty()||deleted.get().getDeleted()==true)
            responseObserver.onNext(Automatic.newBuilder().setAuto(false).build());
        else
        {
            deleted.get().setDeleted(true);
            synchronousClient.deleteAllForAccommodation(AccommodationId.newBuilder().setId(request.getId()).build());
            accommodationRepository.save(deleted.get());
            responseObserver.onNext(Automatic.newBuilder().setAuto(true).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getRentableIntervalById(RentableIntervalIdReq request, StreamObserver<RentableIntervalResp> responseObserver) {
        Optional<RentableInterval> rentableInterval = rentableIntervalRepository.findById(request.getId());
        if(rentableInterval.isPresent())
            responseObserver.onNext(
                    RentableIntervalResp.newBuilder()
                            .setId(rentableInterval.get().getId())
                            .setAccommodationId(rentableInterval.get().getAccommodationId())
                            .setStartTime(rentableInterval.get().getStartTime().toString())
                            .setEndTime(rentableInterval.get().getEndTime().toString())
                            .setPriceOfAccommodation(rentableInterval.get().getPriceOfAccommodation())
                            .setPricePerGuest(rentableInterval.get().getPricePerGuest())
                            .setAutomaticAcceptance(rentableInterval.get().getAutomaticAcceptance())
                            .build()
            );
        else responseObserver.onNext(
                RentableIntervalResp.newBuilder().build()
        );
        responseObserver.onCompleted();
    }

}
