import javax.mail.MessagingException;

public class Main {

    public static void main(String[] args) {
        var parser = new ArgParser(args);
        var mailer = new Mailer(parser.getSenderAddress(), parser.getPassword());

        try {
            var message = "Hallo %s,\nhier ist deine personalisierte Nachricht.";
            for (var user : parser.getUsers()) {
                mailer.sendMail(user.email(), message.formatted(user.name()));
            }
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
