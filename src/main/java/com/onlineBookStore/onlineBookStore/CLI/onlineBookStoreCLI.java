package com.onlineBookStore.onlineBookStore.CLI;

import com.onlineBookStore.onlineBookStore.entities.Book;
import com.onlineBookStore.onlineBookStore.entities.Review;
import com.onlineBookStore.onlineBookStore.entities.User;
import com.onlineBookStore.onlineBookStore.services.BookService;
import com.onlineBookStore.onlineBookStore.services.CartService;
import com.onlineBookStore.onlineBookStore.services.UserService;
import com.onlineBookStore.onlineBookStore.services.ReviewService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Scanner;

@Component
public class onlineBookStoreCLI {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ReviewService reviewService;

    private User currentUser;
    private final Scanner scanner = new Scanner(System.in);

    @PostConstruct
    public void start() {
//         Create a new user as admin
//         userService.createAdmin("admin", "admin123");
//         System.out.println("Default admin user created: Username: admin, Password: admin123");
//         bookService.addBooks();
        while (true) {
            if (currentUser == null) {
                showWelcomeMenu();
            } else {
                if ("admin".equals(currentUser.getRole())) {
                    displayAdminMenu();
                } else {
                    showMainMenu();
                }
            }
        }
    }



    // Login Page
    private void showWelcomeMenu() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Welcome to Online Book Store!");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println(StringUtils.repeat('_', 50));
        System.out.print("Enter your choice: ");

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine()); // Read the whole line and try to parse it as an integer

                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> {
                        System.out.println(StringUtils.repeat('=', 50));
                        System.out.println("Thank you for visiting the Online Bookstore. Goodbye!");
                        System.out.println(StringUtils.repeat('=', 50));
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                break; // Exit the loop once a valid choice is made
            } catch (NumberFormatException e) {
                System.out.println(StringUtils.repeat('_', 50));
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private void login() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Login To Your Account");
        System.out.println(StringUtils.repeat('=', 50));

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println(StringUtils.repeat('_', 50));


        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");

        } else {
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void register() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Register A New User Account");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println(StringUtils.repeat('_', 50));


        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");

        if (userService.register(user)) {
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("Registration successful. Please login.");
        } else {
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("Registration failed. Username might already exist.");
        }
    }



    // User Main Menu
    private void showMainMenu() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Main Menu:");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("1. Book Catalogue");
        System.out.println("2. Search Book by ID");
        System.out.println("3. My Books");
        System.out.println("4. Shopping Cart");
        System.out.println("5. Manage Profile");
        System.out.println("6. Logout");
        System.out.println(StringUtils.repeat('_', 50));
        System.out.print("Enter your choice: ");

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> showBookCatalogue();
                    case 2 -> searchBookById();
                    case 3 -> showMyBooks();
                    case 4 -> showShoppingCart();
                    case 5 -> manageProfile();
                    case 6 -> logout();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void showBookCatalogue() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Book Catalogue");
        System.out.println(StringUtils.repeat('=', 50));

        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("No books available.");
        }  else {
            // Print table headers
            System.out.printf("%-5s %-30s %-25s %-10s %-10s%n", "ID", "Title", "Author", "Quantity", "Price");
            System.out.println(StringUtils.repeat('-', 80)); // A line separator for better clarity

            // Loop through the list of books and print them in a formatted table
            for (Book book : books) {
                System.out.printf("%-5d %-30s %-25s %-10d %-10.2f%n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
            }
        }
    }

    private void searchBookById() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Search Book by ID");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = bookService.getBookById(bookId);
        if (book == null) {
            System.out.println(StringUtils.repeat('-', 50));
            System.out.println("Book not found.");
            return;
        }
        System.out.println(StringUtils.repeat('-', 50));
        System.out.printf("ID: %d, Title: %s, Author: %s, Quantity: %d, Price: %.2f%n",
                book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
        System.out.println(StringUtils.repeat('-', 50));

        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Actions To Do With This Book:");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("1. View Reviews and Ratings");
        System.out.println("2. Add Book to Cart");
        System.out.println("3. Purchase Book");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> {
                List<Review> reviews = reviewService.getReviewsForBook(bookId);
                if (reviews.isEmpty()) {
                    System.out.println("No reviews for this book.");
                } else {
                    System.out.println(StringUtils.repeat('-', 50));
                    System.out.printf("%-5s %-30s%n", "Rating", "Comment");
                    System.out.println(StringUtils.repeat('-', 50));
                    reviews.forEach(review ->
                            System.out.printf("%-5s %-30s%n", review.getRating(), review.getComment())
                    );
                }
            }

            case 2 -> {
                boolean success = cartService.addBookToCart(currentUser.getId(), book.getId());
                if (!success) {
                    System.out.println("Failed to add book to cart.");
                }
            }
            case 3 -> {
                purchaseBookMenu(book);
            }
            case 4 -> {
                // Return to main menu
            }
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private void showMyBooks() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("My Books");
        System.out.println(StringUtils.repeat('=', 50));
        List<Book> myBooks = bookService.getBooksByUser(currentUser.getId());
        System.out.println(StringUtils.repeat('_', 50));
        if (myBooks.isEmpty()) {
            System.out.println("You do not own any books.");
        } else {
            System.out.println(StringUtils.repeat('-', 50));
            System.out.printf("%-5s %-30s %-25s%n", "ID", "Title", "Author");
            System.out.println(StringUtils.repeat('-', 50));
            for (Book book : myBooks) {
                System.out.printf("%-5d %-30s %-25s%n",
                        book.getId(), book.getTitle(), book.getAuthor());
            }
            System.out.println(StringUtils.repeat('-', 50));
            System.out.print("Add review to a book? Enter book ID or 0 to skip: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            if (bookId > 0) {
                System.out.print("Enter rating (1-5): ");
                int rating = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter review: ");
                String comment = scanner.nextLine();
                reviewService.addReview(currentUser.getId(), bookId, rating, comment);
            }
        }
    }

    private void showShoppingCart() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Shopping Cart");
        System.out.println(StringUtils.repeat('=', 50));
        List<Book> cartBooks = cartService.getBooksInCart(currentUser.getId());
        System.out.println(StringUtils.repeat('_', 50));
        if (cartBooks.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println(StringUtils.repeat('-', 50));
            System.out.printf("%-5s %-30s %-25s %-10s%n", "ID", "Title", "Author", "Price");
            System.out.println(StringUtils.repeat('-', 50));
            for (Book book : cartBooks) {
                System.out.printf("%-5d %-30s %-25s %-10s%n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getPrice());
            }
            System.out.println(StringUtils.repeat('-', 50));
            System.out.print("Remove a book from cart? Enter book ID or 0 to skip: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            if (bookId > 0) {
                boolean removed = cartService.removeBookFromCart(currentUser.getId(), bookId);
                System.out.println(removed ? "Book removed from cart." : "Book not found in cart.");
            }
        }
    }

    private void manageProfile() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Manage Profile");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        boolean isUpdated = userService.updateProfile(currentUser.getId(), newUsername, newPassword);

        if (isUpdated) {
            System.out.println(StringUtils.repeat('_', 50));
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Failed to update profile. Please try again.");
        }
    }

    private void logout() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Logging out. Goodbye, " + currentUser.getUsername() + "!");
        currentUser = null;
    }


    public void purchaseBookMenu(Book book) {
        boolean paymentSuccessful = false;

        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Purchasing Book");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Book: " + book.getTitle());
        System.out.println("Price: RM" + book.getPrice());

        while (!paymentSuccessful) {
            System.out.println("Enter payment amount for the book (Price: " + book.getPrice() + "): ");
            double paymentAmount = scanner.nextDouble();
            scanner.nextLine();

            // Call the purchaseBook method with the entered payment amount
            System.out.println(StringUtils.repeat('_', 50));
            paymentSuccessful = bookService.purchaseBook(currentUser.getId(), book.getId(), paymentAmount);

            if (!paymentSuccessful) {
                System.out.println(StringUtils.repeat('_', 50));
                System.out.println("Payment failed. The amount is insufficient.");
                System.out.println("Would you like to try again? (Y/N)");

                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("N")) {
                    System.out.println("Transaction canceled.");
                    break; // Exit the loop and cancel the payment process
                } else if (choice.equals("Y")) {
                    System.out.println("Please try again.");
                } else {
                    System.out.println("Invalid choice. Please enter Y to try again or N to cancel.");
                }
            }
        }
    }








    // Admin Page
    private void displayAdminMenu() {
        while (true) {
            System.out.println(StringUtils.repeat('=', 50));
            System.out.println("Admin Menu");
            System.out.println(StringUtils.repeat('=', 50));
            System.out.println("1. Add a new book");
            System.out.println("2. Edit a book");
            System.out.println("3. Delete a book");
            System.out.println("4. View book catalogue");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    editBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    showBookCatalogue();
                    break;
                case 5:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewBook() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Adding New Book");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();

        bookService.addBook(title, author, price, quantity);
        System.out.println(StringUtils.repeat('-', 50));
        System.out.println("Book added successfully.");
    }

    private void editBook() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Editing Book Entry");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter book ID to edit: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            System.out.println(StringUtils.repeat('-', 50));
            System.out.println("Book not found.");
            return;
        }

        System.out.print("Enter new title (or press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new author (or press Enter to keep current): ");
        String newAuthor = scanner.nextLine();
        System.out.print("Enter new price (or -1 to keep current): ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter new quantity (or -1 to keep current): ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        bookService.editBook(bookId,
                newTitle.isEmpty() ? book.getTitle() : newTitle,
                newAuthor.isEmpty() ? book.getAuthor() : newAuthor,
                newPrice < 0 ? book.getPrice() : newPrice,
                newQuantity < 0 ? book.getQuantity() : newQuantity);

        System.out.println("Book updated successfully.");
    }

    private void deleteBook() {
        System.out.println(StringUtils.repeat('=', 50));
        System.out.println("Deleting Book Entry");
        System.out.println(StringUtils.repeat('=', 50));
        System.out.print("Enter book ID to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book book = bookService.getBookById(bookId);
        if (book == null) {
            System.out.println(StringUtils.repeat('-', 50));
            System.out.println("Book not found.");
            return;
        }

        // Display book details
        System.out.println(StringUtils.repeat('-', 50));
        System.out.printf("Are you sure you want to delete the following book?%n");
        System.out.printf("ID: %d, Title: %s, Author: %s, Price: %.2f, Quantity: %d%n",
                book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getQuantity());
        System.out.println(StringUtils.repeat('-', 50));
        System.out.print("Type 'yes' to confirm, or 'no' to cancel: ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("yes".equals(confirmation)) {
            boolean deleted = bookService.deleteBook(bookId);
            if (deleted) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Failed to delete the book. It may no longer exist.");
            }
        } else {
            System.out.println("Delete operation canceled.");
        }
    }




}
