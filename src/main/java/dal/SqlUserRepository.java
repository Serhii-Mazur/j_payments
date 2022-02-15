package dal;

import application.domain.User;
import application.port.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SqlUserRepository implements UserRepository {
    private final Connection dbConnection;

    public SqlUserRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> getUsers() throws SQLUserRepositoryException {
        throw new SQLUserRepositoryException(String.format("Method <%s> not implemented yet!", "getUsers"));
    }

    @Override
    public void addUser(User user) {
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
    }

    @Override
    public User addUser(String fullName, String eMail, String phoneNumber) throws SQLUserRepositoryException {
        throw new SQLUserRepositoryException(String.format("Method <%s> not implemented yet!", "User addUser"));
//        String ADD_USER_QUERY = "INSERT INTO mono.users (full_name, email, phone_number) VALUES (?, ?, ?)";
//        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_USER_QUERY)) {
//            preparedStatement.setString(1, fullName);
//            preparedStatement.setString(2, eMail);
//            preparedStatement.setString(3, phoneNumber);
//
//            if (preparedStatement.execute()) {
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
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
