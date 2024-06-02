package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "OPERATIONS")
public abstract class Operation extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    protected Operation() { }

    public Operation(User user) {
        this.user = user;
    }
}
