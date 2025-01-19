package com.onlineBookStore.onlineBookStore.controllers;

import com.onlineBookStore.onlineBookStore.entities.Book;
import com.onlineBookStore.onlineBookStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    @Autowired
    private CartService cartService;

    // Endpoint to get the books in the user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<Book>> getBooksInCart(@PathVariable int userId) {
        List<Book> booksInCart = cartService.getBooksInCart(userId);  // Calls CartService's method
        if (booksInCart.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no books found
        }
        return ResponseEntity.ok(booksInCart); // Return 200 with the list of books
    }

    // Endpoint to add a book to the user's cart
    @PostMapping("/add")
    public ResponseEntity<String> addBookToCart(@RequestParam int userId, @RequestParam int bookId) {
        boolean isAdded = cartService.addBookToCart(userId, bookId);  // Calls CartService's method
        if (isAdded) {
            return ResponseEntity.ok("Book added to cart successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to add book to cart.");
        }
    }

    // Endpoint to remove a book from the user's cart
    @PostMapping("/remove")
    public ResponseEntity<String> removeBookFromCart(@RequestParam int userId, @RequestParam int bookId) {
        boolean isRemoved = cartService.removeBookFromCart(userId, bookId);  // Calls CartService's method
        if (isRemoved) {
            return ResponseEntity.ok("Book removed from cart successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove book from cart.");
        }
    }
}
