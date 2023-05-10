package com.devProblems.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.devProblems.*;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {
    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
        TempDB.getAuthorsFromTempDb()
                .stream()
                .filter(author -> author.getAuthorId() == request.getAuthorId())
                .findFirst()
                .ifPresent(responseObserver::onNext);        //On next uvek treba
//ovo gore je moglo i ovako da se napise
    /*     responseObserver.onNext(TempDB.getAuthorsFromTempDb().stream().filter(author -> author.getAuthorId() == request.getAuthorId())
                .findFirst().get());
    */
        responseObserver.onCompleted();// on completed uvek treba
    }

}
