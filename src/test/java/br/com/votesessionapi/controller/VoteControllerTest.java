package br.com.votesessionapi.controller;

import br.com.votesessionapi.exception.AlreadyVotedException;
import br.com.votesessionapi.exception.CustomExceptionHandler;
import br.com.votesessionapi.exception.SessionClosedException;
import br.com.votesessionapi.model.*;
import br.com.votesessionapi.request.VoteRequest;
import br.com.votesessionapi.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
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


public class VoteControllerTest {

    @InjectMocks
    private VoteController voteController;

    @Mock
    private VoteService voteService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voteController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void voteRegistration_ShouldReturnOk_WhenVoteIsRegistered() throws Exception {

        Long sessionId = 1L;
        Long topicId = 1L;
        Long memberId = 1L;
        VoteEnum voteEnum = VoteEnum.YES;

        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setSessionId(sessionId);
        voteRequest.setTopicId(topicId);
        voteRequest.setMemberId(memberId);
        voteRequest.setVote(voteEnum);

        Vote vote = new Vote();
        vote.setId(1L);

        Member member = new Member();
        member.setId(1L);
        member.setCpf("70225495082");


        Topic topic = new Topic();
        topic.setId(1L);
        topic.setCreatedBy(member);
        topic.setDescription("New Topic");

        Session session = new Session();
        session.setId(1L);
        session.setTopic(topic);

        vote.setMember(member);
        vote.setSession(session);
        vote.setTopic(topic);
        vote.setVoteEnum(voteEnum);

        when(voteService.voteRegistration(sessionId, topicId, memberId, voteEnum.getCode())).thenReturn(vote);

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpfMember").value("70225495082"))
                .andExpect(jsonPath("$.topic").value("New Topic"))
                .andExpect(jsonPath("$.message").value("Vote registered successfully"));

    }

    @Test
    public void voteRegistration_ShouldReturnConflict_WhenSessionIsClosed() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setSessionId(1L);
        voteRequest.setTopicId(1L);
        voteRequest.setMemberId(1L);
        voteRequest.setVote(VoteEnum.YES);

        when(voteService.voteRegistration(voteRequest.getSessionId()
        ,voteRequest.getTopicId()
        , voteRequest.getMemberId()
        ,voteRequest.getVote().getCode())).thenThrow(new SessionClosedException("Voting session is closed"));

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void voteRegistration_ShouldReturnConflict_WhenMemberAlreadyVoted() throws Exception {

        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setSessionId(1L);
        voteRequest.setTopicId(1L);
        voteRequest.setMemberId(1L);
        voteRequest.setVote(VoteEnum.YES);


        when(voteService.voteRegistration(voteRequest.getSessionId()
                ,voteRequest.getTopicId()
                , voteRequest.getMemberId()
                ,voteRequest.getVote().getCode())).thenThrow(new AlreadyVotedException("Members have already voted on this topic"));


        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequest)))
                .andExpect(status().isConflict());
    }
}