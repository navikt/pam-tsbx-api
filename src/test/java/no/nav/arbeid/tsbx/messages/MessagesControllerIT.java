package no.nav.arbeid.tsbx.messages;

import com.nimbusds.jwt.SignedJWT;
import no.nav.arbeid.tsbx.Application;
import no.nav.arbeid.tsbx.mocks.MockOAuth2ServerInitializer;
import no.nav.security.mock.oauth2.MockOAuth2Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = MockOAuth2ServerInitializer.class)
public class MessagesControllerIT {

    private final TestRestTemplate restTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockOAuth2Server mockOAuth2Server;

    @Autowired
    public MessagesControllerIT(Environment env) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        testRestTemplate.setUriTemplateHandler(new LocalHostUriTemplateHandler(env));
        this.restTemplate =  testRestTemplate;
    }

    @Test
    public void testGetMessages() {

        final SignedJWT apiAccessToken =
                mockOAuth2Server.issueToken("tokendings", "Test User", "pam-tsbx-api", Map.of("pid", "00000000000"));

        ResponseEntity<List<Message>> response = restTemplate.exchange(
                RequestEntity.get("/api/v1/messages")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiAccessToken.serialize())
                        .build(),
                new ParameterizedTypeReference<>() {});

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testNoAccess() {

        ResponseEntity<Void> response = restTemplate.exchange(
                RequestEntity.get("/api/v1/messages").build(), Void.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}
