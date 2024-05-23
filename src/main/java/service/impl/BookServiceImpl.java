package service.impl;

import model.Book;
import model.PhysicalBook;
import model.DigitalBook;
import model.AudioBook;
import service.BookService;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
    private List<Book> bookInventory;

    public BookServiceImpl() {
        this.bookInventory = new ArrayList<>();
    }

    @Override
    public void addPhysicalBook(PhysicalBook book) {
        bookInventory.add(book);
    }

    @Override
    public void addDigitalBook(DigitalBook book) {
        bookInventory.add(book);
    }

    @Override
    public void addAudioBook(AudioBook book) {
        bookInventory.add(book);
    }

    @Override
    public void removeBook(Book book) {
        bookInventory.remove(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookInventory);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : bookInventory) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : bookInventory) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    @Override
    public List<Book> searchBooksByGenre(String genre) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : bookInventory) {
            if (book.getGenre().contains(genre)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    @Override
    public boolean checkAvailability(Book book) {
        return book.isAvailability();
    }
}
