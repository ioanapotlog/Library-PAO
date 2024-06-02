package service;

import java.sql.Connection;

public interface DigitalBookService {
    public void addDigitalBook(Connection con);

    public void showDigitalBook(Connection con, String title);

    public void editDigitalBook(Connection con, long bookId);

    public void deleteDigitalBook(Connection con, long bookId);
}
