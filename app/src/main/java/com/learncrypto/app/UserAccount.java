package com.learncrypto.app;

public class UserAccount {
    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
    private int score;
    private int level;


    public UserAccount(int id, String firstName, String lastName, String email, String password, int score, int level) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.password = password;
        this.score = score;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }
}
