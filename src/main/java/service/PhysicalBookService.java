package service;

import java.sql.Connection;

public interface PhysicalBookService {
    public void addPhysicalBook(Connection con);

    public void showBookByTitle(Connection con, String title);

    public void editPhysicalBook(Connection con, long bookId);

    public void deletePhysicalBook(Connection con, long bookId);

}
