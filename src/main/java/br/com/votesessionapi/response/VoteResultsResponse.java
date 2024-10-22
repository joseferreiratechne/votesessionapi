package br.com.votesessionapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultsResponse {
    private long totalVotes;
    private long yesVotes;
    private long noVotes;

    @Override
    public String toString() {
        return String.format("Total de Votos: %d, Votos Sim: %d, Votos Não: %d", totalVotes, yesVotes, noVotes);
    }
}