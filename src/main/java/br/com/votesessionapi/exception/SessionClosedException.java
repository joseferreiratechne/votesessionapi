package br.com.votesessionapi.exception;

public class SessionClosedException extends RuntimeException{
    public SessionClosedException(String message){
        super(message);
    }
}
