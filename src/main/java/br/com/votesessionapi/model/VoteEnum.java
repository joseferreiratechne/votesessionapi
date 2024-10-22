package br.com.votesessionapi.model;

import lombok.Getter;

@Getter
public enum VoteEnum {
    YES(1, "Sim", "Yes"),
    NO(2, "Não", "No");

    private final int code;
    private final String portugueseLabel;
    private final String englishLabel;

    VoteEnum(int code, String portugueseLabel, String englishLabel) {
        this.code = code;
        this.portugueseLabel = portugueseLabel;
        this.englishLabel = englishLabel;
    }

    public static VoteEnum fromCode(int code) {
        for (VoteEnum vote : VoteEnum.values()) {
            if (vote.getCode() == code) {
                return vote;
            }
        }
        throw new IllegalArgumentException("Invalid VoteEnum code: " + code);
    }
}

