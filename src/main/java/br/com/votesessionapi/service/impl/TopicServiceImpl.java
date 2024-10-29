package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.model.Topic;
import br.com.votesessionapi.repository.MemberRepository;
import br.com.votesessionapi.repository.TopicRepository;
import br.com.votesessionapi.service.TopicService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final MemberRepository memberRepository;

    @Override
    public Topic createTopic(String description, Long createdBy) {
        Topic topic = new Topic();
        topic.setDescription(description);
        var member = memberRepository.findById(createdBy)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        topic.setCreatedBy(member);
        return topicRepository.save(topic);
    }
}
