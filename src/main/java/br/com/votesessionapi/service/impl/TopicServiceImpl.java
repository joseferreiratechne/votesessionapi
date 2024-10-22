package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.model.Topic;
import br.com.votesessionapi.repository.TopicRepository;
import br.com.votesessionapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}
