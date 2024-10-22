package br.com.votesessionapi.service;

public interface CpfValidationService {
    boolean canVote(String cpf);
}
