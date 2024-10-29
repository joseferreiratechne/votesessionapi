package br.com.votesessionapi.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TopicResponse {

    private Long id;

    private String description;

    private String message;
}
