package model;

import java.util.Date;

public class Review {
    private Integer rating;
    private String comment;
    private User reviewer;
    private Book book;
    private Date reviewDate;

    public Review(Integer rating, String comment, User reviewer, Book book, Date reviewDate) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.book = book;
        this.reviewDate = reviewDate;
    }
}
