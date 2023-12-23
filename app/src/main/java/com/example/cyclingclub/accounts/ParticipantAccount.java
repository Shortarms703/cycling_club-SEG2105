package com.example.cyclingclub.accounts;

public class ParticipantAccount extends Account {
    private String dateOfBirth;
    private String level;

    public ParticipantAccount(String user, String password, String email,
                              String first, String last, Role role,
                              String date, String level
    ) {
        this.username = user;
        this.password = password;
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.role = role;
        this.dateOfBirth = date;
        this.level = level;
    }

    public String getUsername(){return username;}

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public void logIn(String username, String password) {

    }

    @Override
    public void logOut() {

    }

    public Boolean validateDateOfBirth(String dateOfBirth) {
        return null;
    }

    public Boolean validateLevel(String level) {
        return null;
    }

}
