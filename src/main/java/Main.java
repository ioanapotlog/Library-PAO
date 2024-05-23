import model.Book;
import model.PhysicalBook;
import model.DigitalBook;
import model.AudioBook;
import service.BookService;
import service.impl.BookServiceImpl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Conectarea la baza de date:
        DbFunctions db = new DbFunctions();
        Connection connection = db.connect_to_db("LibraryDb", "postgres", "ioanapotlog0627");

//        try {
//            connection.close();
//        }
//        catch(Exception e) {
//            System.out.println(e.getMessage());
//        }

        // Create an instance of BookService
        BookService bookService = new BookServiceImpl();

        // Create some books
        PhysicalBook physicalBook = new PhysicalBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic", true, "Library", 5, "Good", "English", 180);
        DigitalBook digitalBook = new DigitalBook("The Da Vinci Code", "Dan Brown", 2003, "Fantasy", true, "PDF", 2.5, "http://example.com", "English", 500);
        AudioBook audioBook = new AudioBook("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997, "Fantasy", true, "MP3", 100.0, "http://example.com", "Jim Dale", "English", 8);

        // Add books to the inventory
        bookService.addPhysicalBook(physicalBook);
        bookService.addDigitalBook(digitalBook);
        bookService.addAudioBook(audioBook);

        // Search for books by title
        System.out.println("Books with title 'The Da Vinci Code':");
        List<Book> booksByTitle = bookService.searchBooksByTitle("The Da Vinci Code");
        printBooks(booksByTitle);

        // Search for books by author
        System.out.println("\nBooks by author 'J.K. Rowling':");
        List<Book> booksByAuthor = bookService.searchBooksByAuthor("J.K. Rowling");
        printBooks(booksByAuthor);

        // Search for books by genre
        System.out.println("\nBooks in genre 'Fantasy':");
        List<Book> booksByGenre = bookService.searchBooksByGenre("Fantasy");
        printBooks(booksByGenre);
    }

    // Helper method to create a set of genres
    private static Set<String> genres(String... genres) {
        Set<String> genreSet = new HashSet<>();
        for (String genre : genres) {
            genreSet.add(genre);
        }
        return genreSet;
    }

    // Helper method to print list of books
    private static void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }
}
