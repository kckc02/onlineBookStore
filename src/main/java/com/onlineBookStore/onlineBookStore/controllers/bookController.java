package com.onlineBookStore.onlineBookStore.controllers;

import com.onlineBookStore.onlineBookStore.entities.Book;
import com.onlineBookStore.onlineBookStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class bookController {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    // Get a book by its ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<String> addBook(@RequestParam String title,
                                          @RequestParam String author,
                                          @RequestParam double price,
                                          @RequestParam int quantity) {
        bookService.addBook(title, author, price, quantity);
        return ResponseEntity.ok("Book added successfully.");
    }

    // Edit an existing book
    @PutMapping("/{bookId}")
    public ResponseEntity<String> editBook(@PathVariable int bookId,
                                           @RequestParam(required = false) String newTitle,
                                           @RequestParam(required = false) String newAuthor,
                                           @RequestParam(required = false) Double newPrice,
                                           @RequestParam(required = false) Integer newQuantity) {
        boolean updated = bookService.editBook(bookId, newTitle, newAuthor, newPrice, newQuantity);
        if (updated) {
            return ResponseEntity.ok("Book updated successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a book
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        boolean deleted = bookService.deleteBook(bookId);
        if (deleted) {
            return ResponseEntity.ok("Book deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    // Purchase a book
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseBook(@RequestParam int userId,
                                               @RequestParam int bookId,
                                               @RequestParam double paymentAmount) {
        boolean success = bookService.purchaseBook(userId, bookId, paymentAmount);
        if (success) {
            return ResponseEntity.ok("Book purchased successfully.");
        }
        return ResponseEntity.badRequest().body("Purchase failed.");
    }

    // Get books purchased by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUser(@PathVariable int userId) {
        List<Book> books = bookService.getBooksByUser(userId);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
}
