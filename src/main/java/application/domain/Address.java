package application.domain;

import java.util.UUID;

public class Address {
    private UUID addressID;
    private String address;
    private String userEmail;

    public Address(String address, String userEmail) {
        this.address = address;
        this.userEmail = userEmail;
        this.addressID = UUID.randomUUID();
    }

    public UUID getAddressID() {
        return addressID;
    }

    public void setAddressID(UUID addressID) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
