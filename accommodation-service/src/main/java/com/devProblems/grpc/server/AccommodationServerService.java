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
                        .setName(accommodation.getName())
                        .setLocation(accommodation.getLocation())
                        .setPerks(accommodation.getPerks())
                        .setMinGuests(accommodation.getMinGuests())
                        .setMaxGuests(accommodation.getMaxGuests())
                        .build()
        );
        responseObserver.onCompleted();
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
