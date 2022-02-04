package application.service;

import application.domain.Address;
import application.port.AddressRepository;
import application.port.UserRepository;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean addNewAddress(String address, String userEmail) {
        Address newAddress = new Address(address, userEmail);
        return addressRepository.addAddress(newAddress);
    }
}
