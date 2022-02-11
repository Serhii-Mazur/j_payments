package application.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class User {
    private String fullName;
    private String eMail;
    private String phoneNumber;

    public User(String fullName, String eMail, String phoneNumber) {
        this.fullName = fullName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return fullName.equals(user.fullName) && eMail.equals(user.eMail) && phoneNumber.equals(user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, eMail, phoneNumber);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return eMail;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
