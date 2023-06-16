package com.devProblems.grpc.server;

import com.devProblems.*;
import com.devProblems.grpc.server.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.devProblems.grpc.server.model.User;

import java.util.Optional;

@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase{

    @Autowired
    UserRepository repository;
    @GrpcClient("grpc-reservation-service")
    ReservationServiceGrpc.ReservationServiceBlockingStub synchronousReservation;
    @GrpcClient("grpc-accommodation-service")
    AccommodationServiceGrpc.AccommodationServiceBlockingStub synchronousAccommodation;

    @Override
    public void register(UserReq request, StreamObserver<Created> responseObserver) {
        User newUser = new User(request);
        newUser.setProminent(false);
        Optional<User> tempUser = repository.findById(newUser.getUsername());
        if (tempUser.isEmpty()) {
            newUser = repository.save(newUser);
            responseObserver.onNext(Created.newBuilder().setCreated(true).build());
        }else{
            responseObserver.onNext(Created.newBuilder().setCreated(false).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginReq request, StreamObserver<UserTokenStateRes> responseObserver) {
        Optional<User> tempUser = repository.findById(request.getUsername());
        if (tempUser.isEmpty() || !request.getPassword().equals(tempUser.get().getPassword())) {
            responseObserver.onNext(UserTokenStateRes.newBuilder()
                    .setAccessToken("")
                    .setRole("")
                    .build());
        } else {
            responseObserver.onNext(UserTokenStateRes.newBuilder()
                    .setAccessToken(tempUser.get().getUsername())
                    .setRole(tempUser.get().getType().toString())
                    .build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserId request, StreamObserver<UserReq> responseObserver) {
        Optional<User> tempUser = repository.findById(request.getUsername());
        responseObserver.onNext(
                UserReq.newBuilder()
                    .setUsername(tempUser.get().getUsername())
                    .setPassword(tempUser.get().getPassword())
                    .setName(tempUser.get().getName())
                    .setSurname(tempUser.get().getSurname())
                    .setEmail(tempUser.get().getEmail())
                    .setResidency(tempUser.get().getResidency())
                    .setType(tempUser.get().getType())
                    .build());
        responseObserver.onCompleted();
    }

    @Override
    public void edit(EditReq request, StreamObserver<UserTokenStateRes> responseObserver) {
        User newUser = new User(request);
        Optional<User> tempUser = repository.findById(newUser.getUsername());
        //  provjera da li je promijenjen username                                              //  provjera da li u bazi postoji novi username
        if (!request.getUsername().equals(request.getOldUsername()) && !tempUser.isEmpty()  && request.getUsername().equals(tempUser.get().getUsername())) {
            responseObserver.onNext(
                    UserTokenStateRes.newBuilder()
                            .setAccessToken("")
                            .setRole("")
                            .build());
        }else{
            newUser = repository.save(newUser);
            if(!request.getUsername().equals(request.getOldUsername())){
                Optional<User> oldUser = repository.findById(request.getOldUsername());
                repository.delete(oldUser.get());
            }
            responseObserver.onNext(
                    UserTokenStateRes.newBuilder()
                            .setAccessToken(request.getUsername())
                            .setRole(request.getType().toString())
                            .build());
        }
        responseObserver.onCompleted();
    }

      @Override
      public void getCancelCount(UsernameMsg request, StreamObserver<CancelCountMsg> responseObserver) {
          Optional<User> tempUser = repository.findById(request.getUsername());
          if (!tempUser.isEmpty()) {
              responseObserver.onNext(CancelCountMsg.newBuilder()
                      .setCancelCount(tempUser.get().getCancelCount())
                      .build());
          } else {
              responseObserver.onError(new Exception("username doesn't exist"));
          }
              responseObserver.onCompleted();

    }

    @Override
    public void increaseCancelCount(UsernameMsg request, StreamObserver<CancelCountMsg> responseObserver) {
            User tempUser = repository.findById(request.getUsername()).get();
            tempUser.setCancelCount(tempUser.getCancelCount()+1);
            repository.save(tempUser);
            responseObserver.onNext(CancelCountMsg.newBuilder().setCancelCount(tempUser.getCancelCount()).build());
            responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(UserId request, StreamObserver<Created> responseObserver) {
            User user = repository.findById(request.getUsername()).get();
            if(user.getType()==UserType.GUEST)
            {
                allPending accepted=synchronousReservation.showAllAcceptedReservations
                        (UsernameReq.newBuilder().setUsername(request.getUsername()).build());
                if(accepted.getPendingList().isEmpty())
                {
                    repository.deleteById(user.getUsername());
                    responseObserver.onNext(Created.newBuilder().setCreated(true).build());
                }
                else
                    responseObserver.onNext(Created.newBuilder().setCreated(false).build());
            }
            else if(user.getType()==UserType.HOST)
            {
                ListOfAccommodationResp accommodations= synchronousAccommodation.getAccommodationsByHostId(HostIdReq.newBuilder().setHostId(user.getUsername()).build());
                for (var acc:accommodations.getAccommodationsList()
                     ) {
                    allPending allAccepted=synchronousReservation.showAllAcceptedReservationsAccommodation(AccommodationId.newBuilder().setId(acc.getId()).build());
                        if(!allAccepted.getPendingList().isEmpty()){
                            responseObserver.onNext(Created.newBuilder().setCreated(false).build());
                            responseObserver.onCompleted();
                            return;
                        }
                }
                for (var acc:accommodations.getAccommodationsList()
                ) {
                    synchronousAccommodation.deleteAccommodation(AccommodationIdReq.newBuilder().setId(acc.getId()).build());
                }
                repository.deleteById(user.getUsername());
                responseObserver.onNext(Created.newBuilder().setCreated(true).build());
            }
            responseObserver.onCompleted();
    }

    @Override
    public void getProminentStatus(UserId request, StreamObserver<Prominent> responseObserver){
        User user = repository.findById(request.getUsername()).get();

        // ovdje dobavljanje 1.

        // ovdje dobavljanje 2.

        // ovdje dobavljanje 3.

        // ovdje dobavljanje 4.

        // ako ispuni sve uslove uradi ovo
        user.setProminent(true);
        repository.save(user);
        responseObserver.onNext(Prominent.newBuilder().setProminent(true).build());

        // u suprotnom uradi ovo
        // responseObserver.onNext(Prominent.newBuilder().setProminent(false).build());


        responseObserver.onCompleted();
    }

}
