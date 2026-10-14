package com.example.Fooddelivery.dto;

public record AddressResponse (Long addressId, String houseNumber,String street,String city,String pincode,Long customerId) { }
