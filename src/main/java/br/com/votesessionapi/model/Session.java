package br.com.votesessionapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voting_session_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @JsonIgnore
    @Column(name = "starting_voting_session")
    private LocalDateTime startVotingSession;
    @JsonIgnore
    @Column(name = "ending_voting_session")
    private LocalDateTime endVotingSession;
    @Column(nullable = false)
    private boolean isOpen;

    public void openSession(long durationInMinutes) {
        this.startVotingSession = LocalDateTime.now();
        this.endVotingSession = this.startVotingSession.plusMinutes(durationInMinutes);
        this.isOpen = true;
    }

    public boolean isOpen() {
        return isOpen && LocalDateTime.now().isBefore(endVotingSession); // Verifica se a sessão ainda está aberta
    }

    public void closeSession() {
        this.isOpen = false; // Método para fechar a sessão
    }
}
