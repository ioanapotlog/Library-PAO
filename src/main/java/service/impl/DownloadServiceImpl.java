package service.impl;

import service.DownloadService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadServiceImpl implements DownloadService {
    @Override
    public void downloadBook(Connection con, long userId, String bookTitle) {
        BookServiceImpl bookService = new BookServiceImpl();

        PreparedStatement pstmtDownload = null;
        PreparedStatement pstmtOperation = null;
        ResultSet rs = null;
        try {
            long bookId = bookService.getBookIdByTitle(con, bookTitle);
            if (bookId == -1) {
                System.out.println("Book not found.");
                return;
            }

            String insertOperationSql = "INSERT INTO OPERATIONS (USER_ID) VALUES (?)";
            pstmtOperation = con.prepareStatement(insertOperationSql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmtOperation.setLong(1, userId);

            pstmtOperation.executeUpdate();

            rs = pstmtOperation.getGeneratedKeys();
            long operationId = 0;
            if (rs.next()) {
                operationId = rs.getLong(1);
            }

            String insertDownloadSql = "INSERT INTO DOWNLOADS (ID, DOWNLOAD_DATE, DOWNLOADED_BOOK_ID) VALUES (?, ?, ?)";
            pstmtDownload = con.prepareStatement(insertDownloadSql);

            pstmtDownload.setLong(1, operationId);
            pstmtDownload.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            pstmtDownload.setLong(3, bookId);

            pstmtDownload.executeUpdate();

            System.out.println("Download registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtOperation != null) pstmtOperation.close();
                if (pstmtDownload != null) pstmtDownload.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}