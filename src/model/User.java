package model;

public class User {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;

    public User(String lastName, String firstName, String phoneNumber, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
