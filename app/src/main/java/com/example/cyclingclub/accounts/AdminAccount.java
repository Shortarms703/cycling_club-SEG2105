package com.example.cyclingclub.accounts;

public class AdminAccount extends Account {

    public AdminAccount(String user, String password, String email,
                              String first, String last, Role role
    ) {
        this.username = user;
        this.password = password;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.role = role;
    }

    @Override
    public void logIn(String username, String password) {

    }

    @Override
    public void logOut() {

    }
}
