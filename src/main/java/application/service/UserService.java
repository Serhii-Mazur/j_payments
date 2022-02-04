package application.service;

import application.domain.User;

public interface UserService {
    boolean addNewUser(String fullName, String eMail, String phoneNumber);

}
