package application.domain;

import java.util.UUID;

public class User {
    private UUID userID = UUID.randomUUID();
    private String fullName;
    private String eMail;
    private String phoneNumber;

    public User(String fullName, String eMail, String phoneNumber) {
        this.fullName = fullName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
