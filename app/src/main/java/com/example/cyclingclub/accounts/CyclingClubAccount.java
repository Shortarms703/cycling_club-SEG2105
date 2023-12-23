package com.example.cyclingclub.accounts;

import java.util.regex.Pattern;

public class CyclingClubAccount extends Account {
    private String clubName;
    private String address;
    private String postalCode;

    public CyclingClubAccount(String user, String password, String email,
                              String first, String last, Role role,
                              String club, String address, String postalCode
    ) {
        this.username = user;
        this.password = password;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.role = role;
        this.clubName = club;
        this.address = address;
        this.postalCode = postalCode;
    }


    @Override
    public void logIn(String username, String password) {

    }

    @Override
    public void logOut() {

    }

    public Boolean validateAddress(String address) {
        return null;
    }


    public Boolean validatePostalCode() {
        String postalCodePattern = "^[A-Za-z]\\d[A-Za-z]\\d[A-Za-z]\\d$";
        return Pattern.matches(postalCodePattern, postalCode);
    }


    public String getClubName() {
        return clubName;
    }


    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }
}


