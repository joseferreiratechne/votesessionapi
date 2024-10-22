package br.com.votesessionapi.repository;

import br.com.votesessionapi.model.Vote;
import br.com.votesessionapi.model.VoteEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    boolean existsBySessionIdAndMemberId(Long sessionId, Long memberId);

    long countBySessionIdAndVoteEnum(Long sessionId, VoteEnum vote);

    List<Vote> findBySessionId(Long sessionId);
}
