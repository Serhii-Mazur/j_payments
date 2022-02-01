package application.domain;

import java.util.UUID;

public class Address {
    private UUID addressID;
    private String address;
    private UUID userID;

    public Address(String address, UUID userID) {
        this.address = address;
        this.userID = userID;
        this.addressID = UUID.fromString(address + userID);
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

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
