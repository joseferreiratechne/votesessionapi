package br.com.votesessionapi.controller;

import br.com.votesessionapi.model.Session;
import br.com.votesessionapi.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Create sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/{topicId}/open")
    @Operation(summary = "Opens a new voting session")
    public ResponseEntity<Session> openSession(@Parameter(description = "Topic ID", required = true)@PathVariable Long topicId,
                                               @Parameter(description = "Duration", required = true)@RequestParam(defaultValue = "1") long duration) {
        return ResponseEntity.ok(sessionService.openSession(topicId, duration));
    }
}
