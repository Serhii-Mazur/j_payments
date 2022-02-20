package application.service;

import dal.SqlAddressRepository;

public interface AddressService {
    void addNewAddress(String address, String userEmail) throws SqlAddressRepository.SQLAddressRepositoryException;
}
