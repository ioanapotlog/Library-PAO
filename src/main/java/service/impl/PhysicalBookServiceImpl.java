package service.impl;

import service.PhysicalBookService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PhysicalBookServiceImpl extends BookServiceImpl implements PhysicalBookService {

    // CREATE
    @Override
    public void addPhysicalBook(Connection con) {
        PreparedStatement pstmtBook = null;
        PreparedStatement pstmtPhysicalBook = null;
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
            System.out.print("Enter the library location: ");
            String location = scanner.nextLine();
            System.out.print("Enter the number of books available: ");
            int copyCount = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter the condition of the book: ");
            String condition = scanner.nextLine();
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
            pstmtBook.setString(5, "physical");

            // Execute the insert statement for BOOKS
            pstmtBook.executeUpdate();

            // Retrieve the generated book ID
            rs = pstmtBook.getGeneratedKeys();
            int bookId = 0;
            if (rs.next()) {
                bookId = rs.getInt(1);
            }

            String insertPhysicalBookSql = "INSERT INTO PHYSICAL_BOOKS (ID, LOCATION, COPY_COUNT, CONDITION, LANGUAGE, PAGE_COUNT) VALUES (?, ?, ?, ?, ?, ?)";
            pstmtPhysicalBook = con.prepareStatement(insertPhysicalBookSql);

            pstmtPhysicalBook.setInt(1, bookId);
            pstmtPhysicalBook.setString(2, location);
            pstmtPhysicalBook.setInt(3, copyCount);
            pstmtPhysicalBook.setString(4, condition);
            pstmtPhysicalBook.setString(5, language);
            pstmtPhysicalBook.setInt(6, pageCount);

            pstmtPhysicalBook.executeUpdate();

            System.out.println("Physical book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtBook != null) pstmtBook.close();
                if (pstmtPhysicalBook != null) pstmtPhysicalBook.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ
    @Override
    public void showBookByTitle(Connection con, String title) {
        super.showBookByTitle(con, title); // Afișează informațiile de bază

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT LOCATION, COPY_COUNT, CONDITION, LANGUAGE, PAGE_COUNT " +
                    "FROM PHYSICAL_BOOKS p " +
                    "JOIN BOOKS b ON p.ID = b.ID " +
                    "WHERE b.TITLE = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String location = rs.getString("LOCATION");
                int copyCount = rs.getInt("COPY_COUNT");
                String condition = rs.getString("CONDITION");
                String language = rs.getString("LANGUAGE");
                int pageCount = rs.getInt("PAGE_COUNT");

                System.out.println("Location: " + location);
                System.out.println("Copy Count: " + copyCount);
                System.out.println("Condition: " + condition);
                System.out.println("Language: " + language);
                System.out.println("Page Count: " + pageCount);
            } else {
                System.out.println("No physical book found with the given title.");
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
    public void editPhysicalBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new location (or press Enter to keep current): ");
            String location = scanner.nextLine();
            System.out.print("Enter new copy count (or press Enter to keep current): ");
            String copyCountStr = scanner.nextLine();
            System.out.print("Enter new condition (or press Enter to keep current): ");
            String condition = scanner.nextLine();
            System.out.print("Enter new language (or press Enter to keep current): ");
            String language = scanner.nextLine();
            System.out.print("Enter new page count (or press Enter to keep current): ");
            String pageCountStr = scanner.nextLine();

            String updateSql = "UPDATE PHYSICAL_BOOKS SET LOCATION = ?, COPY_COUNT = ?, CONDITION = ?, LANGUAGE = ?, PAGE_COUNT = ? WHERE ID = ?";
            pstmt = con.prepareStatement(updateSql);

            pstmt.setString(1, location);
            pstmt.setInt(2, Integer.parseInt(copyCountStr));
            pstmt.setString(3, condition);
            pstmt.setString(4, language);
            pstmt.setInt(5, Integer.parseInt(pageCountStr));
            pstmt.setLong(6, bookId);

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
    public void deletePhysicalBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            String deletePhysicalBookSql = "DELETE FROM PHYSICAL_BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(deletePhysicalBookSql);
            pstmt.setLong(1, bookId);
            pstmt.executeUpdate();

            // Call deleteBook method to delete from BOOKS table
            deleteBook(con, bookId);

            System.out.println("Physical book with ID " + bookId + " deleted successfully.");
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
