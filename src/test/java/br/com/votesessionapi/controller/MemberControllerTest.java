package br.com.votesessionapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.votesessionapi.exception.CustomExceptionHandler;
import br.com.votesessionapi.exception.MemberAlreadyRegisteredException;
import br.com.votesessionapi.model.Member;
import br.com.votesessionapi.request.MemberRequest;
import br.com.votesessionapi.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new CustomExceptionHandler()) // Adiciona o controlador de exceções
                .build();
    }

    @Test
    public void createMember_ShouldReturnCreatedMember() throws Exception {

        String cpf = "70225495082";
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setCpf(cpf);

        Member member = new Member();
        member.setId(1L);
        member.setCpf(cpf);

        when(memberService.createMember(cpf)).thenReturn(member);


        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(memberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.message").value("Member registered successfully"));
    }

    @Test
    public void createMember_ShouldReturnConflict_WhenMemberAlreadyRegistered() throws Exception {

        String cpf = "70225495082";
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setCpf(cpf);

        when(memberService.createMember(cpf)).thenThrow(new MemberAlreadyRegisteredException("Member Already Registered"));

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("MEMBER_ALREADY_REGISTERED"))
                .andExpect(jsonPath("$.message").value("Member Already Registered"));
    }

    @Test
    public void createMember_ShouldReturnBadRequest_WhenCpfIsInvalid() throws Exception {

        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setCpf("123456789"); // CPF inválido

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberRequest)))
                .andExpect(status().isBadRequest()) // Espera um status 400 Bad Request
                .andExpect(jsonPath("$.cpf").value("CPF invalid")); // Verifica a mensagem de erro para o campo cpf
    }
}