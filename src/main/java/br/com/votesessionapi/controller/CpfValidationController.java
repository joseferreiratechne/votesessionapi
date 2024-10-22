package br.com.votesessionapi.controller;

import br.com.votesessionapi.service.CpfValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cpfValidation")
@Tag(name = "CPF Validation")
@RequiredArgsConstructor
public class CpfValidationController {

    private final CpfValidationService cpfValidationService;

    @GetMapping("/{cpf}")
    @Operation(summary = "Check that the CPF is valid for voting")
    public ResponseEntity<Object> cpfValidation(@Parameter(description = "CPF",required = true) @PathVariable String cpf){
        var canVote = cpfValidationService.canVote(cpf);
        if (!canVote){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse ("UNABLE_TO_VOTE"));
        }else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("ABLE_TO_VOTE"));
        }
    }

    @Setter
    @Getter
    public static class ApiResponse {
        private String message;

        public ApiResponse(String message) {
            this.message = message;
        }

    }

}
