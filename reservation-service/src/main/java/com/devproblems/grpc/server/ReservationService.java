package com.devproblems.grpc.server;

import com.devProblems.ReservationReq;
import com.devProblems.ReservationResp;
import com.devProblems.isAvailable;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.ReservationServiceGrpc;

/**
 * @author Dev Problems(A Sarang Kumar Tak)
 * @YoutubeChannel https://www.youtube.com/channel/UCVno4tMHEXietE3aUTodaZQ
 */
@GrpcService
public class ReservationService extends ReservationServiceGrpc.ReservationServiceImplBase {
    @Override
    public void createReservation(ReservationReq request, StreamObserver<ReservationResp> responseObserver) {
        super.createReservation(request, responseObserver);
    }

    @Override
    public void checkAvailability(ReservationReq request, StreamObserver<isAvailable> responseObserver) {
        super.checkAvailability(request, responseObserver);
    }
}
