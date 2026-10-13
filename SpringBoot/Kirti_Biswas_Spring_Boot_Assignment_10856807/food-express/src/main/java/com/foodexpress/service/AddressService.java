package com.foodexpress.service;

import com.foodexpress.entity.Address;
import com.foodexpress.entity.Customer;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.AddressRepository;
import com.foodexpress.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final Logger logger =
            LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public Address addAddress(Long customerId, Address address) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id : " + customerId));

        address.setCustomer(customer);

        return addressRepository.save(address);
    }

    public List<Address> getAddressesByCustomer(Long customerId) {

        return addressRepository.findByCustomerCustomerId(customerId);
    }

    public Address getAddressById(Long addressId) {

        return addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found with id : " + addressId));
    }

    public Address updateAddress(Long addressId,
                                 Address updatedAddress) {

        Address address = getAddressById(addressId);

        address.setHouseNumber(updatedAddress.getHouseNumber());
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setPincode(updatedAddress.getPincode());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {

        Address address = getAddressById(addressId);

        addressRepository.delete(address);
    }
}