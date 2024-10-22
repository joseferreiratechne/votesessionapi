package br.com.votesessionapi.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TopicRequest {

    @NotEmpty
    private String description;
}
