import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@SuppressWarnings("FieldCanBeLocal")
public class Mailer {
    private final String senderAddress;
    private final Properties props;
    private final Authenticator authenticator;
    private final String HOST = "smtp.gmail.com";
    private final String PORT = "587";
    private final String SUBJECT = "SEHR WICHTIGE EMAIL VOM OBERWICHTEL!";

    public Mailer(String senderAddress, String password) {
        this.senderAddress = senderAddress;
        this.props = getProps();
        this.authenticator = getAuthenticator(password);
    }

    private Properties getProps() {
        var props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
    }

    private Authenticator getAuthenticator(String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAddress, password);
            }
        };
    }

    public void sendMail(String recipientAddress, String content) throws MessagingException {
        var session = Session.getDefaultInstance(props, authenticator);

        var mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(senderAddress));
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
        mail.setSubject(SUBJECT);
        mail.setText(content);

        Transport.send(mail);
    }
}
