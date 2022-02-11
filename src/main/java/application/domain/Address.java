package application.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return addressID.equals(address1.addressID) && address.equals(address1.address) && userEmail.equals(address1.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressID, address, userEmail);
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
