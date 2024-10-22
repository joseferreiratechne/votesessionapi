package br.com.votesessionapi.request;

import br.com.votesessionapi.model.VoteEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequest {
    @NotNull
    private Long sessionId;
    @NotNull
    private Long memberId;
    @NotNull
    private VoteEnum vote;
}
