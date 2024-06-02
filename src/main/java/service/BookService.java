package service;

import java.sql.Connection;

public interface BookService {
    void showBookByTitle(Connection con, String title);

    void editBook(Connection con, long bookId);

    void deleteBook(Connection con, long bookId);

    void showAllPhysicalBooks(Connection con);

    void showAllAudioBooks(Connection con);

    void showAllDigitalBooks(Connection con);

    boolean bookExists(Connection con, int bookId);

    Long getBookIdByTitle(Connection con, String title);

    Integer getCopyCountById(Connection con, Long bookId);
}
