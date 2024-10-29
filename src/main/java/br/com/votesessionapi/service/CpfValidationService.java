package br.com.votesessionapi.service;

import br.com.votesessionapi.response.ApiResponse;

public interface CpfValidationService {
    public ApiResponse validateCpf(String cpf);
}
