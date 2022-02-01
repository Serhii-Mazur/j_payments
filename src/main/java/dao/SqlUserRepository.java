package dao;

import application.domain.User;
import application.port.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlUserRepository implements UserRepository {
    private final Connection dbConnection;

    public SqlUserRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public boolean addUser(User user) {

        String id = user.getUserID().toString();
        String full_name = user.getFullName();
        String email = user.getEmail();
        String phone_number = user.getPhoneNumber();

        String ADD_USER_QUERY = String.format("INSERT INTO mono.users (id, full_name, email, phone_number) " +
                "VALUES ('%s', '%s', '%s', '%s');", id, full_name, email, phone_number);

        boolean result;

        try (
                Statement stmt = dbConnection.createStatement()
        ) {
            stmt.executeUpdate(ADD_USER_QUERY);
            result = true;

        } catch (SQLException exception) {
            exception.printStackTrace();
            result = false;
        }
        return result;
    }
}
