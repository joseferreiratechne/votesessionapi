package br.com.votesessionapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class MemberRequest {

    @CPF(message = "CPF invalid")
    private String cpf;
}
