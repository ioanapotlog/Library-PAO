
import service.DigitalBookService;
import service.impl.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void showLibrarianMenu() {
        System.out.println();
        System.out.println("Loading menu...");
        System.out.println();
        System.out.println("Librarian Menu:");
        System.out.println("1 --> Register a Borrowing.");
        System.out.println("2 --> Add a User.");
        System.out.println("3 --> List all Users.");
        System.out.println("4 --> Search User by email.");
        System.out.println("5 --> Add a Book.");
        System.out.println("6 --> List all Books.");
        System.out.println("7 --> Return to Main Menu.");
        System.out.println();
    }

    public static void showUserMenu() {
        System.out.println();
        System.out.println("Loading menu...");
        System.out.println();
        System.out.println("1 --> Register.");
        System.out.println("2 --> Sign in.");
        System.out.println("3 --> Return to Main Menu.");
        System.out.println();
    }

    public static void showMainMenu() {
        System.out.println();
        System.out.println("BookShop");
        System.out.println("Main Menu:");
        System.out.println("1 --> Librarian");
        System.out.println("2 --> User");
        System.out.println("3 --> Exit!");
        System.out.println();
    }

    public static void showBookMenu() {
        System.out.println();
        System.out.println("1 --> Delete Book.");
        System.out.println("2 --> Edit Book.");
        System.out.println("3 --> Return to Librarian Menu");
    }

    public static void showRegisteredUserMenu() {
        System.out.println();
        System.out.println("User Menu:");
        System.out.println("1 --> Show profile.");
        System.out.println("2 --> View all books.");
        System.out.println("3 --> Log out.");
    }

    public static void showUserBookMenu() {
        System.out.println();
        System.out.println("1 --> Download a book.");
        System.out.println("2 --> Review a book.");
        System.out.println("3 --> View reviews.");
        System.out.println("4 --> Return to User Menu.");
    }

    public static void main(String[] args) {
        // Connection to the database:
        DbFunctions db = new DbFunctions();
        Connection connection = db.connect_to_db("LibraryDb", "postgres", "ioanapotlog0627");

        Scanner scanner = new Scanner(System.in);

        UserServiceImpl userService = new UserServiceImpl();
        BookServiceImpl bookService = new BookServiceImpl();

        // Main Menu:
        int mainOption = 0;
        while (mainOption != 3) {
            showMainMenu();

            long userId;

            System.out.print("Please select an option: ");
            mainOption = Integer.parseInt(scanner.nextLine());
            switch (mainOption) {
                // Librarian Menu:
                case 1:
                    int librarianOption = 0;
                    while (librarianOption != 7) {
                        showLibrarianMenu();
                        System.out.print("Please select an option: ");
                        librarianOption = Integer.parseInt(scanner.nextLine());

                        switch (librarianOption) {
                            case 1:
                                System.out.println();
                                System.out.println("Register a borrowing:");

                                BorrowServiceImpl borrowService = new BorrowServiceImpl();
                                userService = new UserServiceImpl();

                                System.out.println("Enter user Phone Number: ");
                                String phoneNumber = scanner.nextLine();
                                userId = userService.getUserIdByPhoneNumber(connection, phoneNumber);

                                // If the user is not registered, prompt the librarian to register the user
                                if (userId == -1) {
                                    System.out.println();
                                    System.out.println("User not found. Returning to Librarian Menu...");
                                    break;
                                }

                                // Prompt the librarian to enter book titles (comma separated)
                                System.out.print("Enter book titles (comma separated): ");
                                String[] bookTitles = scanner.nextLine().split(",");

                                // Get the IDs of the books based on their titles
                                List<Long> bookIds = new ArrayList<>();
                                for (String title : bookTitles) {
                                    long bookId = bookService.getBookIdByTitle(connection, title.trim());
                                    if (bookId != -1) {
                                        int copyCount = bookService.getCopyCountById(connection, bookId);
                                        if (copyCount > 0) {
                                            bookIds.add(bookId);
                                        } else {
                                            System.out.println("Book with title '" + title + "' is not available for borrowing.");
                                        }
                                    } else {
                                        System.out.println("Book with title '" + title + "' not found.");
                                    }
                                }

                                if (bookIds.isEmpty()) {
                                    System.out.println("No books selected or available for borrowing.");
                                    break;
                                }

                                try {
                                    borrowService.borrowBook(connection, userId, bookIds);
                                    System.out.println("Borrowing registered successfully!");
                                } catch (SQLException e) {
                                    System.out.println("Error registering borrowing: " + e.getMessage());
                                }
                                break;

                            // Add user
                            case 2:
                                System.out.println();
                                System.out.println("Add a user:");
                                UserServiceImpl user = new UserServiceImpl();
                                user.addUser(connection);
                                break;

                            // Show all registered users
                            case 3:
                                System.out.println();
                                userService.showUsers(connection);

                                System.out.print("Press any key to return to user menu: ");
                                scanner.nextLine();
                                break;

                            // Search user by email
                            case 4:
                                String email;

                                System.out.println("\nSearch the user. Enter email:");
                                email = scanner.nextLine();

                                UserServiceImpl searchUser = new UserServiceImpl();
                                userId = searchUser.findUserByEmail(connection, email);

                                if (userId != -1) {
                                    int subOption = 0;
                                    while (subOption != 3 && subOption != 2) {
                                        System.out.println();
                                        System.out.println("Options:");
                                        System.out.println("1 --> Edit user.");
                                        System.out.println("2 --> Delete user.");
                                        System.out.println("3 --> Return to librarian menu.");
                                        System.out.print("Please select an option: ");
                                        subOption = Integer.parseInt(scanner.nextLine());

                                        switch (subOption) {
                                            // Edit user
                                            case 1:
                                                System.out.println("\nEdit user:");
                                                searchUser.updateUserbyEmail(connection, email);
                                                break;
                                            // Delete user
                                            case 2:
                                                searchUser.deleteUserByEmail(connection, email);
                                                break;
                                            case 3:
                                                System.out.println("\nReturning to librarian menu...");
                                                break;
                                            default:
                                                System.out.println("Invalid option. Please select again.");
                                                break;
                                        }
                                    }
                                }
                                break;

                            // Add a book
                            case 5:
                                System.out.println("\nAdd a book:");
                                System.out.println("Select the type of book to add:");
                                System.out.println("1 --> Physical Book");
                                System.out.println("2 --> Digital Book");
                                System.out.println("3 --> Audio Book");

                                int bookType = Integer.parseInt(scanner.nextLine());

                                switch (bookType) {
                                    case 1:
                                        System.out.println("Adding a Physical Book:");
                                        PhysicalBookServiceImpl physicalBookService = new PhysicalBookServiceImpl();
                                        physicalBookService.addPhysicalBook(connection);
                                        break;
                                    case 2:
                                        System.out.println("Adding a Digital Book:");
                                        DigitalBookServiceImpl digitalBookService = new DigitalBookServiceImpl();
                                        digitalBookService.addDigitalBook(connection);
                                        break;
                                    case 3:
                                        System.out.println("Adding an Audio Book:");
                                        AudioBookServiceImpl audioBookService = new AudioBookServiceImpl();
                                        audioBookService.addAudioBook(connection);
                                        break;
                                    default:
                                        System.out.println("Invalid option. Returning to librarian menu...");
                                        break;
                                }
                                break;

                            // Show all books (by category)
                            case 6:
                                System.out.println("\nPlease select the type of books you want to list:");
                                String type = scanner.nextLine();
                                int bookId;

                                bookService = new BookServiceImpl();
                                switch (type) {
                                    case "physical":
                                        bookService.showAllPhysicalBooks(connection);
                                        break;
                                    case "audio":
                                        bookService.showAllAudioBooks(connection);
                                        break;
                                    case "digital":
                                        bookService.showAllDigitalBooks(connection);
                                        break;
                                    default:
                                        System.out.println("Invalid option. Returning to librarian menu...");
                                        break;
                                }

                                if (type.equals("physical") || type.equals("audio") || type.equals("digital")) {
                                    showBookMenu();
                                    System.out.println("Please select an option:");
                                    int option = Integer.parseInt(scanner.nextLine());
                                    switch (option) {
                                        // Delete book
                                        case 1:
                                            System.out.println("Select the ID of the book you want to delete:");
                                            bookId = Integer.parseInt(scanner.nextLine());

                                            if (bookService.bookExists(connection, bookId)) {
                                                switch (type) {
                                                    case "physical":
                                                        PhysicalBookServiceImpl physicalBookService = new PhysicalBookServiceImpl();
                                                        physicalBookService.deletePhysicalBook(connection, bookId);
                                                        break;
                                                    case "audio":
                                                        AudioBookServiceImpl audioBookService = new AudioBookServiceImpl();
                                                        audioBookService.deleteAudioBook(connection, bookId);
                                                        break;
                                                    case "digital":
                                                        DigitalBookServiceImpl digitalBookService = new DigitalBookServiceImpl();
                                                        digitalBookService.deleteDigitalBook(connection, bookId);
                                                        break;
                                                }
                                            }
                                            else {
                                                System.out.println("Invalid id. Returning to librarian menu...");
                                            }
                                            break;
                                        // Edit book
                                        case 2:
                                            System.out.println("Select the ID of the book you want to edit:");
                                            bookId = Integer.parseInt(scanner.nextLine());

                                            if (bookService.bookExists(connection, bookId)) {
                                                switch (type) {
                                                    case "physical":
                                                        PhysicalBookServiceImpl physicalBookService = new PhysicalBookServiceImpl();
                                                        physicalBookService.editPhysicalBook(connection, bookId);
                                                        break;
                                                    case "audio":
                                                        AudioBookServiceImpl audioBookService = new AudioBookServiceImpl();
                                                        audioBookService.editAudioBook(connection, bookId);
                                                        break;
                                                    case "digital":
                                                        DigitalBookServiceImpl digitalBookService = new DigitalBookServiceImpl();
                                                        digitalBookService.editDigitalBook(connection, bookId);
                                                        break;
                                                }
                                            }
                                            else {
                                                System.out.println("Invalid id. Returning to librarian menu...");
                                            }
                                            break;
                                        case 3:
                                            System.out.println("\nReturning to librarian menu...");
                                            break;
                                        default:
                                            System.out.println("Invalid option. Returning to librarian menu...");
                                            break;
                                    }
                                }
                                break;

                            case 7:
                                System.out.println();
                                System.out.println("Returning to Main Menu...");
                                break;

                            default:
                                System.out.println("Invalid option. Please select again.");
                                break;
                        }
                    }
                    break;

                // User Menu:
                case 2:
                    int userOption = 0;
                    String title;
                    ReviewServiceImpl reviewService = new ReviewServiceImpl();

                    while (userOption != 3) {
                        showUserMenu();
                        System.out.print("Please select an option: ");
                        userOption = Integer.parseInt(scanner.nextLine());

                        int subUserOption = 0;

                        switch (userOption) {
                            // User register:
                            case 1:
                                System.out.println();
                                System.out.println("Enter your information:");
                                UserServiceImpl user = new UserServiceImpl();
                                userId = user.addUser(connection);

                                System.out.println();
                                System.out.println("You have successfully registered.");

                                while (subUserOption != 3) {
                                    showRegisteredUserMenu();
                                    System.out.print("Please select an option: ");
                                    subUserOption = Integer.parseInt(scanner.nextLine());
                                    switch (subUserOption) {
                                        // Show user profile
                                        case 1:
                                            System.out.println();
                                            user.findUserById(connection, userId);

                                            System.out.print("Press any key to return to user menu: ");
                                            scanner.nextLine();
                                            break;

                                        // Show all books
                                        case 2:
                                            System.out.println();
                                            System.out.println("Books available:");
                                            bookService = new BookServiceImpl();
                                            bookService.showAllDigitalBooks(connection);
                                            bookService.showAllAudioBooks(connection);

                                            int userSubOption = 0;
                                            while (userSubOption != 4) {
                                                showUserBookMenu();
                                                System.out.print("Please select an option: ");
                                                userSubOption = Integer.parseInt(scanner.nextLine());
                                                switch (userSubOption) {
                                                    // Download a book
                                                    case 1:
                                                        System.out.println();
                                                        System.out.println("Enter the book title:");
                                                        title = scanner.nextLine();

                                                        DownloadServiceImpl downloadService = new DownloadServiceImpl();
                                                        downloadService.downloadBook(connection, userId, title);

                                                        System.out.println("You have successfully downloaded the book!");

                                                        DigitalBookService digitalBookService = new DigitalBookServiceImpl();
                                                        digitalBookService.showDigitalBook(connection, title);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;
                                                    // Review a book
                                                    case 2:
                                                        System.out.println();
                                                        System.out.println("\nEnter the book title:");
                                                        title = scanner.nextLine();

                                                        reviewService.reviewBook(connection, userId, title);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;
                                                    // By entering the title, show book information and reviews
                                                    case 3:
                                                        System.out.println();
                                                        System.out.println("\nEnter the book title:");
                                                        title = scanner.nextLine();
                                                        bookService.showBookByTitle(connection, title);

                                                        long bookId = bookService.getBookIdByTitle(connection, title);
                                                        reviewService.showReviewByBookId(connection, bookId);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;
                                                    case 4:
                                                        System.out.println();
                                                        System.out.println("Returning to Book list...");
                                                        break;
                                                    default:
                                                        System.out.println("Invalid option. Please select again.");
                                                        break;
                                                }
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Signing out...");
                                            break;
                                        default:
                                            System.out.println("Invalid option. Please select again.");
                                            break;
                                    }
                                }
                                break;

                            // User Sign in:
                            case 2:
                                System.out.println();
                                System.out.println("Enter Phone number:");
                                String phoneNumber = scanner.nextLine();

                                boolean verify = userService.userExists(connection, phoneNumber);
                                userId = userService.getUserIdByPhoneNumber(connection, phoneNumber);

                                while (!verify) {
                                    System.out.println("Please try again: ");
                                    phoneNumber = scanner.nextLine();

                                    verify = userService.userExists(connection, phoneNumber);
                                    userId = userService.getUserIdByPhoneNumber(connection, phoneNumber);
                                }
                                System.out.println();
                                System.out.println("You are now signed in!");

                                int bookOption = 0;
                                while (bookOption != 3) {
                                    showRegisteredUserMenu();
                                    System.out.print("Please select an option: ");
                                    bookOption = Integer.parseInt(scanner.nextLine());
                                    switch (bookOption) {
                                        // Show user profile
                                        case 1:
                                            System.out.println();
                                            userService.findUserById(connection, userId);

                                            System.out.print("Press any key to return to user menu: ");
                                            scanner.nextLine();
                                            break;

                                        // Show all books
                                        case 2:
                                            System.out.println();
                                            System.out.println("Books available:");
                                            bookService = new BookServiceImpl();

                                            subUserOption = 0;
                                            while (subUserOption != 4) {
                                                bookService.showAllDigitalBooks(connection);
                                                bookService.showAllAudioBooks(connection);
                                                showUserBookMenu();

                                                System.out.print("Please select an option: ");
                                                subUserOption = Integer.parseInt(scanner.nextLine());
                                                switch (subUserOption) {
                                                    // Download a book
                                                    case 1:
                                                        System.out.println();
                                                        System.out.println("Enter the book title:");
                                                        title = scanner.nextLine();

                                                        DownloadServiceImpl downloadService = new DownloadServiceImpl();
                                                        downloadService.downloadBook(connection, userId, title);

                                                        System.out.println("You have successfully downloaded the book!");

                                                        DigitalBookService digitalBookService = new DigitalBookServiceImpl();
                                                        digitalBookService.showDigitalBook(connection, title);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;

                                                    // Review a book
                                                    case 2:
                                                        System.out.println();
                                                        System.out.println("\nEnter the book title:");
                                                        title = scanner.nextLine();

                                                        reviewService.reviewBook(connection, userId, title);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;

                                                    // By entering the title, show book information and reviews
                                                    case 3:
                                                        System.out.println();
                                                        System.out.println("\nEnter the book title:");
                                                        title = scanner.nextLine();
                                                        bookService.showBookByTitle(connection, title);

                                                        long bookId = bookService.getBookIdByTitle(connection, title);
                                                        reviewService.showReviewByBookId(connection, bookId);

                                                        System.out.print("Press any key to return to user menu: ");
                                                        scanner.nextLine();
                                                        break;

                                                    case 4:
                                                        System.out.println();
                                                        System.out.println("Returning to User Menu...");
                                                        break;
                                                    default:
                                                        System.out.println("Invalid option. Please select again.");
                                                        break;
                                                }
                                            }
                                            break;

                                        case 3:
                                            System.out.println();
                                            System.out.println("Signing out...");
                                            break;

                                        default:
                                            System.out.println();
                                            System.out.println("Invalid option. Please select again.");
                                            break;
                                    }
                                }
                                break;

                            case 3:
                                System.out.println();
                                System.out.println("Returning to Main Menu...");
                                break;

                            default:
                                System.out.println("Invalid option. Please select again.");
                                break;
                        }
                    }
                    break;

                // Exit:
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please select again.");
                    break;
            }
        }
        scanner.close();
    }
}
