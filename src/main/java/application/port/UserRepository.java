package application.port;

import application.domain.User;
import dao.SqlUserRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserRepository {
    List<User> getUsers() throws SqlUserRepository.SQLUserRepositoryException;

    boolean addUser(@NotNull User user);
    User addUser(String fullName, String eMail, String phoneNumber) throws SqlUserRepository.SQLUserRepositoryException;
}
