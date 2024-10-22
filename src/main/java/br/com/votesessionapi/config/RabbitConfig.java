package br.com.votesessionapi.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String VOTE_SESSION_QUEUE = "vote-session-queue";
    public static final String VOTE_SESSION_DEAD_QUEUE = "vote-session-dead-queue";
    public static final String VOTE_SESSION_EXCHANGE = "vote-session-exchange";

    @Bean
    public Queue voteSessionQueue() {
        return QueueBuilder.durable(VOTE_SESSION_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", VOTE_SESSION_DEAD_QUEUE)
                .build();
    }

    @Bean
    public Queue voteSessionDeadQueue() {
        return QueueBuilder.durable(VOTE_SESSION_DEAD_QUEUE).build();
    }

    @Bean
    public TopicExchange voteSessionExchange() {
        return new TopicExchange(VOTE_SESSION_EXCHANGE);
    }

    @Bean
    public Binding voteSessionBinding(Queue voteSessionQueue, TopicExchange voteSessionExchange) {
        return BindingBuilder.bind(voteSessionQueue).to(voteSessionExchange).with(VOTE_SESSION_QUEUE);
    }
}
