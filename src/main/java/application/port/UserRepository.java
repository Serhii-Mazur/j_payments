package application.port;

import application.domain.User;
import dal.SqlUserRepository;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers() throws SqlUserRepository.SQLUserRepositoryException;

    void addUser(User user) throws SqlUserRepository.SQLUserRepositoryException;
}
