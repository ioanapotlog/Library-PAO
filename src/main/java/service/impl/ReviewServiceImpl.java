package service.impl;

import service.ReviewService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ReviewServiceImpl implements ReviewService {
    @Override
    public void reviewBook(Connection con, long userId, String bookTitle) {
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long bookId = 0;

        try {
            String checkUserSql = "SELECT * FROM USERS WHERE ID = ?";
            pstmt = con.prepareStatement(checkUserSql);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("The user does not exist.");
                return;
            }

            String checkBookSql = "SELECT * FROM BOOKS WHERE TITLE = ?";
            pstmt = con.prepareStatement(checkBookSql);
            pstmt.setString(1, bookTitle);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookId = rs.getLong("ID");
            } else {
                System.out.println("The book does not exist.");
                return;
            }

            System.out.print("Enter the rating (1-5): ");
            int rating = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the comment: ");
            String comment = scanner.nextLine();
            Date reviewDate = new Date();

            // Insert the review into the database
            String insertReviewSql = "INSERT INTO REVIEWS (COMMENT, REVIEWER_ID, BOOK_ID, REVIEW_DATE, RATING) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertReviewSql);

            pstmt.setString(1, comment);
            pstmt.setLong(2, userId);
            pstmt.setLong(3, bookId);
            pstmt.setDate(4, new java.sql.Date(reviewDate.getTime()));
            pstmt.setInt(5, rating);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thank you for the review!");
            } else {
                System.out.println("An error occurred while recording the review.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showReviewByBookId(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT r.*, u.FIRST_NAME, u.LAST_NAME FROM REVIEWS r JOIN USERS u ON u.ID = r.REVIEWER_ID WHERE r.BOOK_ID = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, bookId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String rating = rs.getString("RATING");
                String comment = rs.getString("COMMENT");
                String reviewer = String.format("%s %s", rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"));
                Date reviewDate = rs.getDate("REVIEW_DATE");
                System.out.println("Review by: " + reviewer + ", Rating: " + rating +
                        ", Comment: " + comment + ", Date: " + reviewDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
