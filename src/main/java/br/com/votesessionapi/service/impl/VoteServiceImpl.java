package br.com.votesessionapi.service.impl;

import br.com.votesessionapi.exception.AlreadyVotedException;
import br.com.votesessionapi.exception.SessionClosedException;
import br.com.votesessionapi.model.Vote;
import br.com.votesessionapi.model.VoteEnum;
import br.com.votesessionapi.repository.MemberRepository;
import br.com.votesessionapi.repository.SessionRepository;
import br.com.votesessionapi.repository.VoteRepository;
import br.com.votesessionapi.response.VoteResultsResponse;
import br.com.votesessionapi.service.CpfValidationService;
import br.com.votesessionapi.service.VoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final CpfValidationService cpfValidationService;

    @Override
    public Vote voteRegistration(Long sessionId, Long memberId, int vote) {
        var sessionModel = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        if (!sessionModel.isOpen()){
            throw new SessionClosedException("Voting session is closed");
        }

        var memberModel = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        if(voteRepository.existsBySessionIdAndMemberId(sessionModel.getId(),memberModel.getId())){
            throw new AlreadyVotedException("Members have already voted on this topic");
        }

        Vote voteModel = new Vote();
        voteModel.setSession(sessionModel);
        voteModel.setMember(memberModel);
        voteModel.setVoteEnum(VoteEnum.fromCode(vote));

        return voteRepository.save(voteModel);
    }

    @Override
    public VoteResultsResponse getVotingResults(Long sessionId) {
        List<Vote> votes = voteRepository.findBySessionId(sessionId);
        return calculateResults(votes);
    }

    private VoteResultsResponse calculateResults(List<Vote> votes) {
        long totalVotes = votes.size();
        long yesVotes = votes.stream().filter(vote -> VoteEnum.YES.equals(vote.getVoteEnum())).count();
        long noVotes = votes.stream().filter(vote -> VoteEnum.NO.equals(vote.getVoteEnum())).count();

        return new VoteResultsResponse(totalVotes, yesVotes, noVotes);
    }
}
