package service;

import java.sql.Connection;

public interface DownloadService {
    void downloadBook(Connection con, long userId, String bookTitle);


}
