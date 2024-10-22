package br.com.votesessionapi.consumer;

import br.com.votesessionapi.config.RabbitConfig;
import br.com.votesessionapi.repository.SessionRepository;
import br.com.votesessionapi.service.VoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteSessionConsumer {

    Logger log;

    private final VoteService votingSessionService;

    private final SessionRepository sessionRepository;

    @RabbitListener(queues = RabbitConfig.VOTE_SESSION_DEAD_QUEUE)
    public void processVoteSession(Long sessionId) {
        log.info("Processing voting results for session ID: {}", sessionId);

        var sessionModel = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        // Fecha a sessão imediatamente
        sessionModel.closeSession();
        sessionRepository.save(sessionModel);

        var results = votingSessionService.getVotingResults(sessionId);

        log.info("Voting results for session ID {}: {}", sessionId, results);
    }
}