package com.example.Fooddelivery.dto;
//import jakarta.validation.constraints.NotBlank;


public record AddressRequest( String houseNumber,  String street, String city, String pincode) {}

