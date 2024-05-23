package service;

import model.Book;
import model.PhysicalBook;
import model.DigitalBook;
import model.AudioBook;

import java.util.List;

public interface BookService {
    void addPhysicalBook(PhysicalBook book);
    void addDigitalBook(DigitalBook book);
    void addAudioBook(AudioBook book);

    void removeBook(Book book);

    List<Book> getAllBooks();

    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByAuthor(String author);
    List<Book> searchBooksByGenre(String genre);

    boolean checkAvailability(Book book);
}
