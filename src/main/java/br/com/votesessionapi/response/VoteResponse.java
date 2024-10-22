package br.com.votesessionapi.response;

import lombok.Data;

@Data
public class VoteResponse {

    private String cpfMember;
    private String topic;
    private String message;

}