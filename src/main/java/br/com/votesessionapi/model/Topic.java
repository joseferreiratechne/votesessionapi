package br.com.votesessionapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @Column(name = "topic_description")
    private String description;
}
