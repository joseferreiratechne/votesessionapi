package br.com.votesessionapi.service;

import br.com.votesessionapi.model.Member;

public interface MemberService {
    Member createMember(String cpf);
}
