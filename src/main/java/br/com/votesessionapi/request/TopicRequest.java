package br.com.votesessionapi.request;

import br.com.votesessionapi.model.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopicRequest {

    @NotEmpty
    private String description;

    @NotNull
    private Long createdBy;
}
