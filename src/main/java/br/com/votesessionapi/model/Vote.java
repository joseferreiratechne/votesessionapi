package br.com.votesessionapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "vote", nullable = false)
    private VoteEnum voteEnum;


}
