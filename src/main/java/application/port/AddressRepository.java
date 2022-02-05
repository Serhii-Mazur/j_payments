package application.port;

import application.domain.Address;
import application.domain.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface AddressRepository {
    List<Address> getAddresses(String userEmail);

    UUID getAddressID(String address);

    boolean addAddress(@NotNull Address paymentAddress);
}
