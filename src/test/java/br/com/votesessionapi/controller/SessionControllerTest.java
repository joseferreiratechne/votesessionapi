package br.com.votesessionapi.controller;

import br.com.votesessionapi.exception.CustomExceptionHandler;
import br.com.votesessionapi.model.Session;
import br.com.votesessionapi.service.SessionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void openSession_ShouldReturnOk_WhenSessionIsOpened() throws Exception {
        Long topicId = 1L;
        long duration = 1L;
        Session session = new Session();
        session.setId(1L); // Defina o ID da sessão conforme necessário

        when(sessionService.openSession(topicId, duration)).thenReturn(session);

        mockMvc.perform(post("/api/sessions/{topicId}/open", topicId)
                        .param("duration", String.valueOf(duration))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void openSession_ShouldReturnConflict_WhenSessionAlreadyOpen() throws Exception {

        Long topicId = 1L;
        long duration = 1L;

       when(sessionService.openSession(topicId,duration)).thenThrow(new IllegalStateException("A voting session is already open for this topic."));

        mockMvc.perform(post("/api/sessions/{topicId}/open", topicId)
                        .param("duration", String.valueOf(duration))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void openSession_ShouldReturnNotFound_WhenTopicDoesNotExist() throws Exception {
        Long topicId = 1L;
        long duration = 1L;

        when(sessionService.openSession(topicId,duration)).thenThrow(new EntityNotFoundException("topic not found"));

        mockMvc.perform(post("/api/sessions/{topicId}/open", topicId)
                        .param("duration", String.valueOf(duration))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
