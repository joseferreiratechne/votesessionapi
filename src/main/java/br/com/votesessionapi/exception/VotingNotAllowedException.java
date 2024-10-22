package br.com.votesessionapi.exception;

public class VotingNotAllowedException extends RuntimeException {
    public VotingNotAllowedException(String message) {
        super(message);
    }
}