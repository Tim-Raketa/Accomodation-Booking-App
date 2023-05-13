package com.devProblems.grpc.server;

import com.devProblems.AccommodationReq;
import com.devProblems.AccommodationResp;
import com.devProblems.AccommodationServiceGrpc;
import com.devProblems.grpc.server.model.Accommodation;
import com.devProblems.grpc.server.repo.AccommodationRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AccommodationServerService extends AccommodationServiceGrpc.AccommodationServiceImplBase {

    @Autowired
    AccommodationRepository accommodationRepository;

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
}
