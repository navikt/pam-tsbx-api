package no.nav.arbeid.tsbx.messages;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MessagesService {

    public List<Message> getMessagesForUser(UserId userId) {
        return mockMessages(userId);
    }

    private List<Message> mockMessages(UserId user) {
        final String[] titles = {
                "Hei %s",
                "Hola %s",
                "Vi trenger deg %s"
        };
        final String[] employers = {
                "Danger Delivery AS",
                "Norsk it AS",
                "Vi ringer DU bringer ANS",
                "Ditt Renhold AS",
                "Kulsveen Logistikk AS",
                "Kulsveen Transport AS"
        };
        final String[] texts = {
                "Kan du dele din CV med oss ?",
                "Hei, vi har en viktig melding til deg. Du er utvalgt.",
                "Vi har en bra annonse ute nå, sjekk den ut !",
                "Kan du begynne i dag ? Ring oss på 22222222 ..",
                "Invitasjon til jobbtreff: ..."
        };

        final Random rng = new Random(user.pid().hashCode());
        final List<Message> messages = new ArrayList<>();
        final int nMessages = rng.nextInt(Integer.MAX_VALUE) % 5 + 1;

        for (int i=0; i<nMessages; i++) {
            final String title = titles[rng.nextInt(titles.length)];
            final String employer = employers[rng.nextInt(employers.length)];
            final String text = texts[rng.nextInt(texts.length)];
            messages.add(new Message(title.formatted(user.name()), employer, text));
        }

        return messages;
    }

}
