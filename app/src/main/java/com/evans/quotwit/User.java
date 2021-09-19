package com.evans.quotwit;

public class User {
    String name, email, password, confirmPassword;

    public User() {

    }

    public User(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
