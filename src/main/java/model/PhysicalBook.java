package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "PHYSICAL_BOOKS")
@DiscriminatorValue(value = "physical")
public class PhysicalBook extends Book {
    @Column(name = "LOCATION")
    private String location;

    @Column(name = "COPY_COUNT")
    private Integer copyCount;

    @Column(name = "CONDITION")
    private String condition;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "PAGE_COUNT")
    private Integer pageCount;

    protected PhysicalBook() { }

    public PhysicalBook(String title, String author, Integer publicationYear, String genre, String location, Integer copyCount, String condition, String language, Integer pageCount) {
        super(title, author, publicationYear, genre);
        this.location = location;
        this.copyCount = copyCount;
        this.condition = condition;
        this.language = language;
        this.pageCount = pageCount;
    }

    public Integer getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(Integer copyCount) {
        this.copyCount = copyCount;
    }
}
