package br.com.votesessionapi.service;

import br.com.votesessionapi.model.Topic;

public interface TopicService {
     Topic createTopic(String description, Long createdBy);
}
