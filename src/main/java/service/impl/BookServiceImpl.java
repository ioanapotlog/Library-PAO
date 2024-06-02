package service.impl;

import model.Book;
import service.BookService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookServiceImpl implements BookService {

    // READ
    public void showBookByTitle(Connection con, String title) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT ID, TITLE, AUTHOR, PUBLICATION_YEAR, GENRE, DISCRIMINATOR FROM BOOKS WHERE TITLE = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("ID");
                String bookTitle = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                int publicationYear = rs.getInt("PUBLICATION_YEAR");
                String genre = rs.getString("GENRE");
                String discriminator = rs.getString("DISCRIMINATOR");

                System.out.println("Book ID: " + id);
                System.out.println("Title: " + bookTitle);
                System.out.println("Author: " + author);
                System.out.println("Publication Year: " + publicationYear);
                System.out.println("Genre: " + genre);
                System.out.println("Type: " + discriminator);
            } else {
                System.out.println("No book found with the given title.");
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
    public void editBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new title (or press Enter to keep current): ");
            String title = scanner.nextLine();
            System.out.print("Enter new author (or press Enter to keep current): ");
            String author = scanner.nextLine();
            System.out.print("Enter new publication year (or press Enter to keep current): ");
            String publicationYearStr = scanner.nextLine();
            System.out.print("Enter new genre (or press Enter to keep current): ");
            String genre = scanner.nextLine();

            String updateSql = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, PUBLICATION_YEAR = ?, GENRE = ? WHERE ID = ?";
            pstmt = con.prepareStatement(updateSql);

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, Integer.parseInt(publicationYearStr));
            pstmt.setString(4, genre);
            pstmt.setLong(5, bookId);

            pstmt.executeUpdate();

            System.out.println("Book with ID " + bookId + " updated successfully.");
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
    public void deleteBook(Connection con, long bookId) {
        PreparedStatement pstmt = null;
        try {
            String deleteSql = "DELETE FROM BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(deleteSql);
            pstmt.setLong(1, bookId);
            pstmt.executeUpdate();
            System.out.println("Book with ID " + bookId + " deleted successfully.");
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

    @Override
    public void showAllPhysicalBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT * FROM BOOKS WHERE DISCRIMINATOR = 'physical'";
            pstmt = con.prepareStatement(selectSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                int publicationYear = rs.getInt("PUBLICATION_YEAR");
                String genre = rs.getString("GENRE");
                String discriminator = rs.getString("DISCRIMINATOR");
                System.out.println("Book ID: " + id + ", Title: " + title + ", Author: " + author +
                        ", Publication Year: " + publicationYear + ", Genre: " + genre + ", Type: " + discriminator);
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
    public void showAllAudioBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT * FROM BOOKS WHERE DISCRIMINATOR = 'audio'";
            pstmt = con.prepareStatement(selectSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                int publicationYear = rs.getInt("PUBLICATION_YEAR");
                String genre = rs.getString("GENRE");
                String discriminator = rs.getString("DISCRIMINATOR");
                System.out.println("Book ID: " + id + ", Title: " + title + ", Author: " + author +
                        ", Publication Year: " + publicationYear + ", Genre: " + genre + ", Type: " + discriminator);
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
    public void showAllDigitalBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT * FROM BOOKS WHERE DISCRIMINATOR = 'digital'";
            pstmt = con.prepareStatement(selectSql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                int publicationYear = rs.getInt("PUBLICATION_YEAR");
                String genre = rs.getString("GENRE");
                String discriminator = rs.getString("DISCRIMINATOR");
                System.out.println("Book ID: " + id + ", Title: " + title + ", Author: " + author +
                        ", Publication Year: " + publicationYear + ", Genre: " + genre + ", Type: " + discriminator);
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
    public boolean bookExists(Connection con, int bookId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            String selectSql = "SELECT 1 FROM BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();
            exists = rs.next();
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
        return exists;
    }

    @Override
    public Long getBookIdByTitle(Connection con, String title) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long bookId = null;

        try {
            String query = "SELECT ID FROM BOOKS WHERE TITLE = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookId = rs.getLong("ID");
            } else {
                System.out.println("Book with title " + title + " does not exist.");
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

        return bookId;
    }

    @Override
    public Integer getCopyCountById(Connection con, Long bookId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int copyCount = 0;

        try {
            String query = "SELECT COPY_COUNT FROM PHYSICAL_BOOKS WHERE ID = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, bookId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                copyCount = rs.getInt("COPY_COUNT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return copyCount;
    }
}
