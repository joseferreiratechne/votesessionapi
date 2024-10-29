package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.exception.MemberAlreadyRegisteredException;
import br.com.votesessionapi.model.Member;
import br.com.votesessionapi.repository.MemberRepository;
import br.com.votesessionapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member createMember(String cpf) {
        try {
            Member member = new Member();
            member.setCpf(cpf);
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new MemberAlreadyRegisteredException("Member Already Registered");
        }
    }
}
