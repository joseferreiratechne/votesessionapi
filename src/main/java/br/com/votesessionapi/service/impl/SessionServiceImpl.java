package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.config.RabbitConfig;
import br.com.votesessionapi.model.Session;
import br.com.votesessionapi.repository.SessionRepository;
import br.com.votesessionapi.repository.TopicRepository;
import br.com.votesessionapi.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final AmqpTemplate amqpTemplate;
    private final SessionRepository sessionRepository;
    private final TopicRepository topicRepository;

    @Override
    public Session openSession(Long topicId, Long duration) {

        boolean sessionExists = sessionRepository.existsByTopicIdAndIsOpenTrue(topicId);
        if (sessionExists) {
            throw new IllegalStateException("A voting session is already open for this topic.");
        }

        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("topic not found"));
        Session votingSession = new Session();

            votingSession.setTopic(topic);
            votingSession.openSession(duration);
            var session = sessionRepository.save(votingSession);

            long ttl = duration * 60 * 1000;

        amqpTemplate.convertAndSend(RabbitConfig.VOTE_SESSION_QUEUE, votingSession.getId(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(ttl));
                return message;
            }
        });
            log.info("Voting session opened for session ID: {}", votingSession.getId());

            return session;
    }
}
