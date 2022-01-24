package dao;

import application.domain.User;
import application.port.UserRepository;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlUserRepository implements UserRepository {
    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public boolean addUser(@NotNull User user) {

        String user_id = user.getUserID().toString();
        String full_name = user.getFullName();
        String email = user.geteMail();
        String phone_number = user.getPhoneNumber();

        String ADD_USER_QUERY = String.format("INSERT INTO mono_test_project.users (user_id, full_name, email, phone_number) " +
                "VALUES ('%s', '%s', '%s', '%s');", user_id, full_name, email, phone_number);
        System.out.println(ADD_USER_QUERY);
        boolean result;

        try (
                Connection dbConnection = DatabaseConnector.getDbConnection(new DatabaseConfig());
                Statement stmt = dbConnection.createStatement();
        ) {
            stmt.executeUpdate(ADD_USER_QUERY);
            result = true;

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            result = false;
        }
        return result;
    }
}
