package br.com.votesessionapi.response;

import lombok.Data;


    @Data
    public class ErrorResponse {
        // Getters e setters
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }