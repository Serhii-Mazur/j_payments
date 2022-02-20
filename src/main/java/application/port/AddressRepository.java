package application.port;

import application.domain.Address;
import dal.SqlAddressRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository {
    List<Address> getAddresses(String userEmail);

    List<Address> getAllAddresses() throws SqlAddressRepository.SQLAddressRepositoryException;

    UUID getAddressID(String address) throws SqlAddressRepository.SQLAddressRepositoryException;

    void addAddress(Address paymentAddress) throws SqlAddressRepository.SQLAddressRepositoryException;
}
