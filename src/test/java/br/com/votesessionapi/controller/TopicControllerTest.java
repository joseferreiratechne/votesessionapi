package br.com.votesessionapi.controller;

import br.com.votesessionapi.exception.CustomExceptionHandler;
import br.com.votesessionapi.model.Topic;
import br.com.votesessionapi.request.TopicRequest;
import br.com.votesessionapi.service.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopicControllerTest {

    @InjectMocks
    private TopicController topicController;

    @Mock
    private TopicService topicService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void createTopic_ShouldReturnOk_WhenTopicIsCreated() throws Exception {

        String description = "New Topic";
        Long createdBy = 1L;
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setDescription(description);
        topicRequest.setCreatedBy(createdBy);

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setDescription(description);

        when(topicService.createTopic(description,createdBy)).thenReturn(topic);

        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(topicRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("New Topic"))
                .andExpect(jsonPath("$.message").value("Topic registered successfully"));

    }

    @Test
    public void createTopic_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {

        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setDescription("New Topic");
        topicRequest.setCreatedBy(1L);


        when(topicService.createTopic(topicRequest.getDescription(), topicRequest.getCreatedBy())).thenThrow(new EntityNotFoundException("Member not found"));

        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(topicRequest)))
                .andExpect(status().isNotFound());
    }
}
