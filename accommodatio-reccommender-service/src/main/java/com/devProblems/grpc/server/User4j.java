package com.devProblems.grpc.server;


import com.devProblems.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User4j {

    @Id
    String username;
    
}
