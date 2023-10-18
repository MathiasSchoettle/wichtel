import javax.mail.MessagingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wichtler {
    private final List<User> users;
    private final Mailer mailer;
    private final String message_content;
    private boolean isDebug = false;
    private static final String FILE_NAME = "content.html";

    public Wichtler(String senderAddress, String password, List<User> users) {
        this.users = users;
        this.mailer = new Mailer(senderAddress, password);
        this.message_content = getMessage();
    }
    
    public void process() {
        var users = new ArrayList<>(this.users);
        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i++) {
            var giver = users.get(i);
            var receiver = i + 1 < users.size() ? users.get(i + 1) : users.get(0);

            send(giver, receiver);
        }
    }

    private void send(User giver, User receiver) {
        var content = replacePlaceholders(giver.name(), receiver.name());

        if (isDebug) {
            System.out.println(giver.name() + " -> " + receiver.name());
            return;
        }

        try {
            mailer.sendMail(giver.email(), content);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private String replacePlaceholders(String giverName, String receiverName) {
        var content = message_content;
        content = content.replace("$giver", giverName);
        content = content.replace("$receiver", receiverName);
        return content;
    }

    private String getMessage() {
        URL resourceURL = this.getClass().getClassLoader().getResource(FILE_NAME);

        assert resourceURL != null;

        try {
            var path = Path.of(resourceURL.toURI());
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException("could not load html content", e);
        }
    }

    public void setDebug(boolean flag) {
        this.isDebug = flag;
    }
}
