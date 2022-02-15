package application.port;

import application.domain.Address;

import java.util.List;
import java.util.UUID;

public interface AddressRepository {
    List<Address> getAddresses(String userEmail);

    UUID getAddressID(String address);

    void addAddress(Address paymentAddress);
}
