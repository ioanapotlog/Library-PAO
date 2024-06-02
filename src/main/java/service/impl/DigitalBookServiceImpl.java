package service.impl;

import service.DigitalBookService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DigitalBookServiceImpl extends BookServiceImpl implements DigitalBookService {
    @Override
    public void addDigitalBook(Connection con) {
        PreparedStatement pstmtBook = null;
        PreparedStatement pstmtDigitalBook = null;
        ResultSet rs = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            System.out.print("Enter the publication year: ");
            int publicationYear = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();
            System.out.print("Enter the format: ");
            String format = scanner.nextLine();
            System.out.print("Enter the link to download the book: ");
            String downloadLink = scanner.nextLine();
            System.out.print("Enter language: ");
            String language = scanner.nextLine();
            System.out.print("Enter the number of pages: ");
            int pageCount = scanner.nextInt();
            scanner.nextLine();

            String insertBookSql = "INSERT INTO BOOKS (TITLE, AUTHOR, PUBLICATION_YEAR, GENRE, DISCRIMINATOR) VALUES (?, ?, ?, ?, ?)";
            pstmtBook = con.prepareStatement(insertBookSql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmtBook.setString(1, title);
            pstmtBook.setString(2, author);
            pstmtBook.setInt(3, publicationYear);
            pstmtBook.setString(4, genre);
            pstmtBook.setString(5, "digital");

            // Execute the insert statement for BOOKS
            pstmtBook.executeUpdate();

            // Retrieve the generated book ID
            rs = pstmtBook.getGeneratedKeys();
            int bookId = 0;
            if (rs.next()) {
                bookId = rs.getInt(1);
            }

            String insertPhysicalBookSql = "INSERT INTO DIGITAL_BOOKS (ID, FORMAT, DOWNLOAD_LINK, LANGUAGE, PAGE_COUNT) VALUES (?, ?, ?, ?, ?)";
            pstmtDigitalBook = con.prepareStatement(insertPhysicalBookSql);

            pstmtDigitalBook.setInt(1, bookId);
            pstmtDigitalBook.setString(2, format);
            pstmtDigitalBook.setString(3, downloadLink);
            pstmtDigitalBook.setString(4, language);
            pstmtDigitalBook.setInt(5, pageCount);

            pstmtDigitalBook.executeUpdate();

            System.out.println("Digital book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtBook != null) pstmtBook.close();
                if (pstmtDigitalBook != null) pstmtDigitalBook.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ
    @Override
    public void showDigitalBook(Connection con, String title) {
        super.showBookByTitle(con, title); // Afișează informațiile de bază

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT FORMAT, DOWNLOAD_LINK, LANGUAGE, PAGE_COUNT " +
                    "FROM DIGITAL_BOOKS d " +
                    "JOIN BOOKS b ON d.ID = b.ID " +
                    "WHERE b.TITLE = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String format = rs.getString("FORMAT");
                String downloadLink = rs.getString("DOWNLOAD_LINK");
                String language = rs.getString("LANGUAGE");
                Integer pageCount = rs.getInt("PAGE_COUNT");

                System.out.println("Format: " + format);
                System.out.println("Download link: " + downloadLink);
                System.out.println("Language: " + language);
                System.out.println("Number of pages: " + pageCount);
            } else {
                System.out.println("No digital book found with the given title.");
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

    // UPDATE
    @Override
    public void editDigitalBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new format (or press Enter to keep current): ");
            String format = scanner.nextLine();
            System.out.print("Enter new download link (or press Enter to keep current): ");
            String downloadLink = scanner.nextLine();
            System.out.print("Enter new language (or press Enter to keep current): ");
            String language = scanner.nextLine();
            System.out.print("Enter new page number (or press Enter to keep current): ");
            String pageCount = scanner.nextLine();

            String updateSql = "UPDATE DIGITAL_BOOKS SET FORMAT = ?, DOWNLOAD_LINK = ?, LANGUAGE = ?, PAGE_COUNT = ? WHERE ID = ?";
            pstmt = con.prepareStatement(updateSql);

            pstmt.setString(1, format);
            pstmt.setString(2, downloadLink);
            pstmt.setString(3, language);
            pstmt.setInt(4, Integer.parseInt(pageCount));
            pstmt.setLong(5, bookId);

            pstmt.executeUpdate();

            System.out.println("Physical book with ID " + bookId + " updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    @Override
    public void deleteDigitalBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            String deleteDigitalBookSql = "DELETE FROM DIGITAL_BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(deleteDigitalBookSql);
            pstmt.setLong(1, bookId);
            pstmt.executeUpdate();

            // Call deleteBook method to delete from BOOKS table
            deleteBook(con, bookId);

            System.out.println("Digital book with ID " + bookId + " deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
