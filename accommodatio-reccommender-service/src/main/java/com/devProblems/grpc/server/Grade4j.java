package com.devProblems.grpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade4j {
    @Id
    Long id;
    Integer grade;
    LocalDateTime timeStamp;
    @Relationship(type = "UserGraded",direction = Relationship.Direction.INCOMING)
    User4j user;
}
