package service.impl;

import service.LibrarianService;
import model.PhysicalBook;
import model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibrarianServiceImpl implements LibrarianService {

    @Override
    public void borrowBooks(User user, List<PhysicalBook> books) {
        Date borrowDate = new Date();
        long returnMillis = borrowDate.getTime() + (14 * 24 * 60 * 60 * 1000); // Add 14 days in milliseconds
        Date returnDate = new Date(returnMillis);

        for (PhysicalBook book : books) {
            if (book.getCopyCount() > 0) {
                book.setCopyCount(book.getCopyCount() - 1);
                if (book.getCopyCount() == 0) {
                    book.setAvailability(false);
                }
            } else {
                System.out.println("No copies left for book: " + book.getTitle());
            }
        }

        logBorrowTransaction(user, books, borrowDate, returnDate);
    }

    @Override
    public void returnBooks(User user, List<PhysicalBook> returnedBooks) {
        List<PhysicalBook> borrowedBooks = getBorrowedBooksByUser(user);

        for (PhysicalBook returnedBook : returnedBooks) {
            if (borrowedBooks.contains(returnedBook)) {
                returnedBook.setCopyCount(returnedBook.getCopyCount() + 1);
                returnedBook.setAvailability(true);
            } else {
                System.out.println("Book '" + returnedBook.getTitle() + "' was not borrowed by " + user.getFirstName() + " " + user.getLastName());
            }
        }

        logReturnTransaction(user, returnedBooks);
    }

    public List<PhysicalBook> getBorrowedBooksByUser(User user) {
        return new ArrayList<>();
    }

    public void logBorrowTransaction(User user, List<PhysicalBook> books, Date borrowDate, Date returnDate) {
        System.out.println("Borrow operation logged: User '" + user.getFirstName() + " " + user.getLastName() +
                "' borrowed the following books: " + books.size() + " on " + borrowDate + " and must return them by " + returnDate);
    }

    public void logReturnTransaction(User user, List<PhysicalBook> returnedBooks) {
        System.out.println("Return operation logged: User '" + user.getFirstName() + " " + user.getLastName() +
                "' returned the following books: " + returnedBooks.size());
    }

}
