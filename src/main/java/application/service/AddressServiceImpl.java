package application.service;

import application.domain.Address;
import application.port.AddressRepository;
import dal.SqlAddressRepository;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void addNewAddress(String address, String userEmail) throws SqlAddressRepository.SQLAddressRepositoryException {
        Address newAddress = new Address(address, userEmail);
        addressRepository.addAddress(newAddress);
    }
}
