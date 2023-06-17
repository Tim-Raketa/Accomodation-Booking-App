package com.devProblems.grpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation4j {
    @Id
    Long id;
    @Relationship(type = "AccommodationGrades",direction = Relationship.Direction.INCOMING)
    List<Grade4j> grades;
    @Relationship(type="Visited",direction = Relationship.Direction.INCOMING)
    List<User4j> users;
}
