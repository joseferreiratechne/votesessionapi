package br.com.votesessionapi.controller;

import br.com.votesessionapi.request.VoteRequest;
import br.com.votesessionapi.response.VoteResponse;
import br.com.votesessionapi.response.VoteResultsResponse;
import br.com.votesessionapi.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Registration Vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @Operation(summary = "register your vote")
    public ResponseEntity<VoteResponse> voteRegistration(@Parameter(description = "data to register your vote",required = true)@RequestBody @Valid VoteRequest voteRequest) {
            var vote = voteService.voteRegistration(voteRequest.getSessionId(),
                    voteRequest.getMemberId(),
                    voteRequest.getVote().getCode());
            var response = new VoteResponse();
            response.setCpfMember(vote.getMember().getCpf());
            response.setTopic(vote.getSession().getTopic().getDescription());
            response.setMessage("Vote registered successfully");
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{sessionId}/result")
    @Operation(summary = "get the session result")
    public ResponseEntity<VoteResultsResponse> result(@Parameter(description = "Session ID", required = true)@PathVariable Long sessionId) {
        var result = voteService.getVotingResults(sessionId);
        return ResponseEntity.ok(result);
    }
}
