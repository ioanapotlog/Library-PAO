package service.impl;

import service.BorrowService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {

    @Override
    public void borrowBook(Connection con, long userId, List<Long> bookIds) throws SQLException {
        PreparedStatement pstmtOperation = null;
        PreparedStatement pstmtBorrowing = null;
        PreparedStatement pstmtBorrowedBooks = null;
        PreparedStatement pstmtUpdateCopyCount = null;

        try {
            con.setAutoCommit(false);

            // Insert into OPERATIONS table
            String insertOperationSql = "INSERT INTO OPERATIONS (USER_ID) VALUES (?)";
            pstmtOperation = con.prepareStatement(insertOperationSql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtOperation.setLong(1, userId);
            pstmtOperation.executeUpdate();

            // Get the generated ID for the operation
            long operationId;
            try (var rs = pstmtOperation.getGeneratedKeys()) {
                if (rs.next()) {
                    operationId = rs.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve operation ID.");
                }
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date returnDate = new Date(calendar.getTimeInMillis());

            String insertBorrowingSql = "INSERT INTO BORROWINGS (ID, BORROW_DATE, RETURN_DATE, LIBRARIAN_ID, STATUS) VALUES (?, ?, ?, ?, ?)";
            pstmtBorrowing = con.prepareStatement(insertBorrowingSql);
            pstmtBorrowing.setLong(1, operationId);
            pstmtBorrowing.setDate(2, new Date(System.currentTimeMillis()));
            pstmtBorrowing.setDate(3, returnDate);
            pstmtBorrowing.setLong(4, 100);
            pstmtBorrowing.setString(5, "Borrowed");
            pstmtBorrowing.executeUpdate();

            String insertBorrowedBooksSql = "INSERT INTO BORROWED_BOOKS (BORROWING_ID,  \"borrowedBooks_ID\") VALUES (?, ?)";
            pstmtBorrowedBooks = con.prepareStatement(insertBorrowedBooksSql);
            for (Long bookId : bookIds) {
                pstmtBorrowedBooks.setLong(1, operationId);
                pstmtBorrowedBooks.setLong(2, bookId);
                pstmtBorrowedBooks.executeUpdate();
            }

            String updateCopyCountSql = "UPDATE PHYSICAL_BOOKS SET COPY_COUNT = COPY_COUNT - 1 WHERE ID = ?";
            pstmtUpdateCopyCount = con.prepareStatement(updateCopyCountSql);
            for (Long bookId : bookIds) {
                pstmtUpdateCopyCount.setLong(1, bookId);
                pstmtUpdateCopyCount.executeUpdate();
            }

            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (pstmtOperation != null) pstmtOperation.close();
            if (pstmtBorrowing != null) pstmtBorrowing.close();
            if (pstmtBorrowedBooks != null) pstmtBorrowedBooks.close();
            if (pstmtUpdateCopyCount != null) pstmtUpdateCopyCount.close();
            if (con != null) con.setAutoCommit(true);
        }
    }
}
