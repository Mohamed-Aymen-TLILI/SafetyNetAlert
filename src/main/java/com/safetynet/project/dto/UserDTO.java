package com.safetynet.project.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDTO {

    private static final Logger logger = LogManager.getLogger(UserDTO.class);

    private String lastName;
    private String firstName;
    private String address;
    private String phone;


    public UserDTO() {
    }


    public UserDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "{" +
                " lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
