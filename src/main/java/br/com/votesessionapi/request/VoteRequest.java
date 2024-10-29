package br.com.votesessionapi.request;

import br.com.votesessionapi.model.VoteEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequest {
    @NotNull
    private Long sessionId;
    @NotNull
    private Long topicId;
    @NotNull
    private Long memberId;
    @NotNull
    private VoteEnum vote;
}
