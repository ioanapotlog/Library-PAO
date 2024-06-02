package service.impl;

import service.AudioBookService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AudioBookServiceImpl extends BookServiceImpl implements AudioBookService {

    // CREATE
    @Override
    public void addAudioBook(Connection con) {
        PreparedStatement pstmtBook = null;
        PreparedStatement pstmtAudioBook = null;
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
            System.out.print("Enter format: ");
            String format = scanner.nextLine();
            System.out.print("Enter the link to download the book: ");
            String downloadLink = scanner.nextLine();
            System.out.print("Enter narrator: ");
            String narrator = scanner.nextLine();
            System.out.print("Enter language: ");
            String language = scanner.nextLine();
            System.out.print("Enter the number of hours: ");
            int hours = scanner.nextInt();
            scanner.nextLine();

            String insertBookSql = "INSERT INTO BOOKS (TITLE, AUTHOR, PUBLICATION_YEAR, GENRE, DISCRIMINATOR) VALUES (?, ?, ?, ?, ?)";
            pstmtBook = con.prepareStatement(insertBookSql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmtBook.setString(1, title);
            pstmtBook.setString(2, author);
            pstmtBook.setInt(3, publicationYear);
            pstmtBook.setString(4, genre);
            pstmtBook.setString(5, "audio");

            pstmtBook.executeUpdate();

            // Retrieve the generated book ID
            rs = pstmtBook.getGeneratedKeys();
            int bookId = 0;
            if (rs.next()) {
                bookId = rs.getInt(1);
            }

            String insertPhysicalBookSql = "INSERT INTO AUDIO_BOOKS (ID, FORMAT, DOWNLOAD_LINK, NARRATOR, LANGUAGE, HOURS) VALUES (?, ?, ?, ?, ?, ?)";
            pstmtAudioBook = con.prepareStatement(insertPhysicalBookSql);

            pstmtAudioBook.setInt(1, bookId);
            pstmtAudioBook.setString(2, format);
            pstmtAudioBook.setString(3, downloadLink);
            pstmtAudioBook.setString(4, narrator);
            pstmtAudioBook.setString(5, language);
            pstmtAudioBook.setInt(6, hours);

            pstmtAudioBook.executeUpdate();

            System.out.println("Audio book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtBook != null) pstmtBook.close();
                if (pstmtAudioBook != null) pstmtAudioBook.close();
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
            String selectSql = "SELECT FORMAT, DOWNLOAD_LINK, NARRATOR, LANGUAGE, HOURS " +
                    "FROM AUDIO_BOOKS a " +
                    "JOIN BOOKS b ON a.ID = b.ID " +
                    "WHERE b.TITLE = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String format = rs.getString("FORMAT");
                String downloadLink = rs.getString("DOWNLOAD_LINK");
                String narrator = rs.getString("NARRATOR");
                String language = rs.getString("LANGUAGE");
                int hours = rs.getInt("HOURS");

                System.out.println("Format: " + format);
                System.out.println("Download link: " + downloadLink);
                System.out.println("Narrator: " + narrator);
                System.out.println("Language: " + language);
                System.out.println("Duration: " + hours + "hours");
            } else {
                System.out.println("No audio book found with the given title.");
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
    public void editAudioBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new format (or press Enter to keep current): ");
            String format = scanner.nextLine();
            System.out.print("Enter new download link (or press Enter to keep current): ");
            String downloadLink = scanner.nextLine();
            System.out.print("Enter new narrator (or press Enter to keep current): ");
            String narrator = scanner.nextLine();
            System.out.print("Enter new language (or press Enter to keep current): ");
            String language = scanner.nextLine();
            System.out.print("Enter new duration (or press Enter to keep current): ");
            String hours = scanner.nextLine();

            String updateSql = "UPDATE AUDIO_BOOKS SET FORMAT = ?, DOWNLOAD_LINK = ?, NARRATOR = ?, LANGUAGE = ?, HOURS = ? WHERE ID = ?";
            pstmt = con.prepareStatement(updateSql);

            pstmt.setString(1, format);
            pstmt.setString(2, downloadLink);
            pstmt.setString(3, narrator);
            pstmt.setString(4, language);
            pstmt.setInt(5, Integer.parseInt(hours));
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
    public void deleteAudioBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            String deleteAudioBookSql = "DELETE FROM AUDIO_BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(deleteAudioBookSql);
            pstmt.setLong(1, bookId);
            pstmt.executeUpdate();

            // Call deleteBook method to delete from BOOKS table
            deleteBook(con, bookId);

            System.out.println("Audio book with ID " + bookId + " deleted successfully.");
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
