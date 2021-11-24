package no.nav.arbeid.tsbx.messages;

import no.nav.security.token.support.core.api.Protected;
import no.nav.security.token.support.core.context.TokenValidationContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Protected
public class MessagesController {

    private final MessagesService messagesService;
    private final TokenValidationContextHolder tokenContext;

    public MessagesController(MessagesService messagesService, TokenValidationContextHolder tokenContext) {
        this.messagesService = messagesService;
        this.tokenContext = tokenContext;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        final UserId userId = getUserIdFromRequestContext().orElseThrow(
                () -> new IllegalStateException("No UserId present in protected request context"));

        return ResponseEntity.ok(messagesService.getMessagesForUser(userId));
    }

    private Optional<UserId> getUserIdFromRequestContext() {
        return tokenContext.getTokenValidationContext()
                .getFirstValidToken().map(jwtToken ->
                        new UserId(jwtToken.getSubject(), jwtToken.getJwtTokenClaims().getStringClaim("pid")));
    }

}
