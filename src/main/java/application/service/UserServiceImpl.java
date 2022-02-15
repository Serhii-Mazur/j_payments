package application.service;

import application.domain.User;
import application.port.UserRepository;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addNewUser(String fullName, String eMail, String phoneNumber) {
        User newUser = new User(fullName, eMail, phoneNumber);
        userRepository.addUser(newUser);
    }
}
