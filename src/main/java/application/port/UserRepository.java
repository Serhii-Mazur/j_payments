package application.port;

import application.domain.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    boolean addUser(@NotNull User user);
}
