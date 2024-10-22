package br.com.votesessionapi.controller;

import br.com.votesessionapi.model.Topic;
import br.com.votesessionapi.request.TopicRequest;
import br.com.votesessionapi.response.TopicResponse;
import br.com.votesessionapi.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "Create Topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    @Operation(summary = "create a new member")
    public ResponseEntity<TopicResponse> createTopic(@Parameter(description = "data to create topic",required = true)@RequestBody @Valid TopicRequest topicRequest){
        var topic = new Topic();
        topic.setDescription(topicRequest.getDescription());

        var topicModel = topicService.createTopic(topic);

        var topicResponse = new TopicResponse();

        topicResponse.setDescription(topicModel.getDescription());
        topicResponse.setMessage("Topic registered successfully");

        return ResponseEntity.ok(topicResponse);
    }
}
