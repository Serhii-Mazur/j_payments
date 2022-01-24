package application.port;

import application.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    boolean addUser(User user);
}
