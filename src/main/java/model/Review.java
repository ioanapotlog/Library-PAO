package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REVIEWS")
public class Review extends BaseEntity {

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "COMMENT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "REVIEWER_ID")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Column(name = "REVIEW_DATE")
    private Date reviewDate;

    protected Review() { }

    public Review(Integer rating, String comment, User reviewer, Book book, Date reviewDate) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.book = book;
        this.reviewDate = reviewDate;
    }

    public Integer getRating() {
        return rating;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

}
