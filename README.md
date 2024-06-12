# BookShop

## Description
Java project based on Object-Oriented Programming principles, that integrates PostgreSQL database using JPA Hibernate. 

## Classes
- Books:
  - Physical Book;
  - Digital Book;
  - Audio Book;
- User;
- Librarian;
- Operations:
  - Borrow;
  - Download;
- Review;

## Functionalities
### Main Menu:
Initial choice - When starting the application, the user can choose to log in as a librarian or as a general user.
### Librarian Menu:
- Register a Borrowing - registers the borrowing of a physical book by a user;
- Add new User - allows the librarian to add a new user to the system;
- Show Information - displays information about books (search by type: physical/digital/audio) and users;
- Manage Books - adds, edits or deletes books from the library's system.
### User Menu:
- Sign in or Register - users can either sign in if they already registered by entering their phone number or create aa new account;
- Download Books - registered users can download digital or audio books;
- Leave a Review - registered users can leave reviews for books;
- Show Information - display information about certain books (search by title) or the user's profile.
