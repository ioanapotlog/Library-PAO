package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BorrowService {
    void borrowBook(Connection con, long userId, List<Long> bookIds) throws SQLException;
}
