package main.java.domain;

import java.time.Instant;

public class User {
    private String name;
    private String phoneNumber;
    private String email;
    private Instant lastLogin;

    public User(String name, String phoneNumber, String email, Instant lastLogin) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastLogin = lastLogin;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }
}
