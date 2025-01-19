package com.onlineBookStore.onlineBookStore.implementations;

import com.onlineBookStore.onlineBookStore.entities.Book;
import com.onlineBookStore.onlineBookStore.entities.User;
import com.onlineBookStore.onlineBookStore.repositories.BookRepository;
import com.onlineBookStore.onlineBookStore.repositories.UserRepository;
import com.onlineBookStore.onlineBookStore.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    @Transactional
    public boolean purchaseBook(int userId, int bookId, double paymentAmount) {
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null) {
            System.out.println("User not found.");
            return false;
        }

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        if (book.getQuantity() <= 0) {
            System.out.println("Book is out of stock.");
            return false;
        }

        if (paymentAmount >= book.getPrice()) {
            book.setQuantity(book.getQuantity() - 1);
            user.getBooks().add(book);

            bookRepository.save(book);
            userRepository.save(user);

            System.out.println("Payment successful. Book purchased successfully.");
            return true;
        } else {
            System.out.println("Payment failed. The amount is insufficient.");
            return false;
        }
    }

    @Override
    @Transactional
    public List<Book> getBooksByUser(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return List.of();
        }

        List<Book> books = user.getBooks();
        if (books == null || books.isEmpty()) {
            System.out.println("No books found for this user.");
        }
        return books != null ? books : List.of();
    }

    @Override
    public void addBook(String title, String author, double price, int quantity) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setPrice(price);
        newBook.setQuantity(quantity);

        bookRepository.save(newBook);
        System.out.println("Book added successfully: " + title);
    }

    @Override
    public void addBooks() {
        String[] titles = {"Java Programming", "Spring Boot", "Hibernate Basics", "Advanced Java", "Data Structures", "Algorithm Design", "Python for Data Science", "Effective Java", "Database Management", "Learning SQL", "Software Engineering", "Object-Oriented Design", "Web Development", "Networking Fundamentals", "Design Patterns", "Modern Java", "Spring Security", "Testing in Java", "Java EE", "Clean Code"};

        String[] authors = {"John Doe", "Jane Smith", "Alice Johnson", "Robert Brown", "Emily Davis", "David Wilson", "James Miller", "Olivia Moore", "Michael Taylor", "Linda Anderson"};

        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            String title = titles[random.nextInt(titles.length)];
            String author = authors[random.nextInt(authors.length)];
            double price = 15 + random.nextDouble() * 50;
            int quantity = random.nextInt(10);

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setPrice(price);
            book.setQuantity(quantity);

            bookRepository.save(book);
        }

        System.out.println("20 books have been added to the database.");
    }

    @Override
    public boolean editBook(int bookId, String newTitle, String newAuthor, Double newPrice, Integer newQuantity) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            if (newTitle != null && !newTitle.isEmpty()) {
                book.setTitle(newTitle);
            }
            if (newAuthor != null && !newAuthor.isEmpty()) {
                book.setAuthor(newAuthor);
            }
            if (newPrice != null) {
                book.setPrice(newPrice);
            }
            if (newQuantity != null) {
                book.setQuantity(newQuantity);
            }

            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBook(int bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }
}
