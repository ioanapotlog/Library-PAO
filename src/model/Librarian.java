package model;

public class Librarian {
    private Integer identificationCode;
    private String lastName;
    private String firstName;

    public Librarian(Integer identificationCode, String lastName, String firstName) {
        this.identificationCode = identificationCode;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
