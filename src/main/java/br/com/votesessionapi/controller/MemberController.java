package br.com.votesessionapi.controller;

import br.com.votesessionapi.request.MemberRequest;
import br.com.votesessionapi.response.MemberResponse;
import br.com.votesessionapi.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Create Member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary  = "create a new member")
    public ResponseEntity<MemberResponse> createMember(@Parameter(description = "data to create member",required = true) @RequestBody @Valid MemberRequest memberRequest){
            var memberCreated = memberService.createMember(memberRequest.getCpf());
            var memberResponse = new MemberResponse();
            memberResponse.setId(memberCreated.getId());
            memberResponse.setCpf(memberCreated.getCpf());
            memberResponse.setMessage("Member registered successfully");
            return ResponseEntity.ok(memberResponse);
    }
}
