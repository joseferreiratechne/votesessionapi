package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.response.ApiResponse;
import br.com.votesessionapi.response.UserStatusResponse;
import br.com.votesessionapi.service.CpfValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CpfValidationServiceImpl implements CpfValidationService {

    private final RestTemplate restTemplate;

    private static final String CPF_VALIDATION_URL = "https://run.mocky.io/v3/57f23672-c15f-48f8-90d3-d84ce00250b8/users/";


    @Override
    public ApiResponse validateCpf(String cpf) {
        try {
            // Fazendo a requisição para o endpoint de validação do CPF
            var response = restTemplate.getForObject(CPF_VALIDATION_URL + cpf, UserStatusResponse.class);
            if (response != null && "ABLE_TO_VOTE".equals(response.getStatus())) {
                return new ApiResponse("ABLE_TO_VOTE");
            } else {
                return new ApiResponse("UNABLE_TO_VOTE");
            }
        } catch (HttpClientErrorException.NotFound e) {
            // CPF não encontrado
            return new ApiResponse("UNABLE_TO_VOTE");
        }
    }
}
