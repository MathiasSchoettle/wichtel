import java.util.*;
import java.util.List;

public class ArgParser {
    private String password;
    private String senderAddress;
    private List<User> users;

    public ArgParser(String[] args) {
        var iterator = Arrays.stream(args).iterator();

        while (iterator.hasNext()) {
            var command = iterator.next();

            if (!iterator.hasNext()) throw new RuntimeException("missing argument to command: " + command);

            var value = iterator.next();

            switch (command) {
                case "-p" -> setPassword(value);
                case "-s" -> setSenderAddress(value);
                case "-r" -> setUsers(value);
                default -> throw new RuntimeException("expected command, got: " + command);
            }
        }
    }

    private void setPassword(String value) {
        this.password = value;
    }

    private void setSenderAddress(String value) {
        this.senderAddress = value;
    }

    private void setUsers(String value) {
        this.users = Arrays.stream(value.split(";")).map(nameAndEmail -> {
            var split = nameAndEmail.split(":");
            return new User(split[0], split[1]);
        }).toList();
    }

    public String getPassword() {
        return password;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ArgParser{" +
                "password='" + password + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", users=" + users +
                '}';
    }
}
