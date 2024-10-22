package br.com.votesessionapi.exception;

public class MemberAlreadyRegisteredException extends RuntimeException{
    public MemberAlreadyRegisteredException(String message) {
        super(message);
    }
}
