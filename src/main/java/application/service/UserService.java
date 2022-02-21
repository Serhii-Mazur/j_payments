package application.service;

import dal.SqlUserRepository;

public interface UserService {
    void addNewUser(String fullName, String eMail, String phoneNumber) throws SqlUserRepository.SQLUserRepositoryException;

}
