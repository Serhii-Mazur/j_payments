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
}
