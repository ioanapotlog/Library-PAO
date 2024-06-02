package service;

import java.sql.Connection;

public interface AudioBookService {

    public void addAudioBook(Connection con);

    public void showBookByTitle(Connection con, String title);

    public void editAudioBook(Connection con, long bookId);

    public void deleteAudioBook(Connection con, long bookId);
}
