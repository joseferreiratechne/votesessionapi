package br.com.votesessionapi.response;

import lombok.Data;

@Data
public class MemberResponse {
    private Long id;
    private String message;
    private String cpf;
}
