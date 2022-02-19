package dal;

import application.domain.User;
import application.port.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class SqlUserRepository implements UserRepository {
    private final Connection dbConnection;
    private final Logger logger;

    public SqlUserRepository(Logger logger, Connection dbConnection) {
        this.logger = logger;
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> getUsers() throws SQLUserRepositoryException {
        throw new SQLUserRepositoryException(String.format("Method <%s> not implemented yet!", "getUsers"));
    }

    @Override
    public void addUser(User user) {
        long start = System.nanoTime();
        String full_name = user.getFullName();
        String email = user.getEmail();
        String phone_number = user.getPhoneNumber();

        String ADD_USER_QUERY = "INSERT INTO mono.users (full_name, email, phone_number) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_USER_QUERY)) {
            int pos = 0;
            preparedStatement.setString(++pos, full_name);
            preparedStatement.setString(++pos, email);
            preparedStatement.setString(++pos, phone_number);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        String report = String.format("User added:%n" +
                        "Full name  : %s%n" +
                        "E-mail     : %s%n" +
                        "Phone      : %s%n",
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber());
        logger.info(report + "Operation time: " + ((end - start) / 1000) + " milliseconds.");
    }

    @Override
    public User addUser(String fullName, String eMail, String phoneNumber) throws SQLUserRepositoryException {
        throw new SQLUserRepositoryException(String.format("Method <%s> not implemented yet!", "User addUser"));
    }

    public class SQLUserRepositoryException extends Exception {
        public SQLUserRepositoryException() {
            super();
        }

        public SQLUserRepositoryException(String message) {
            super(message);
        }

        public SQLUserRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
