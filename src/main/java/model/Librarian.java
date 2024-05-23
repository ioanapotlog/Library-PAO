package model;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARIANS")
public class Librarian extends BaseEntity {
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    protected Librarian() { }

    public Librarian(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
