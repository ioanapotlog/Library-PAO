package service;

import java.sql.Connection;

public interface ReviewService {
    void reviewBook(Connection con, long userId, String bookTitle);

    void showReviewByBookId(Connection con, long bookId);
}
