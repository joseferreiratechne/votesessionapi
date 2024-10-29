package br.com.votesessionapi.service;

import br.com.votesessionapi.model.Vote;
import br.com.votesessionapi.model.VoteEnum;
import br.com.votesessionapi.response.VoteResultsResponse;

import java.util.Map;

public interface VoteService {
    Vote voteRegistration(Long sessionId,Long topicId ,Long memberId,int vote);

    public VoteResultsResponse getVotingResults(Long sessionId);
}
