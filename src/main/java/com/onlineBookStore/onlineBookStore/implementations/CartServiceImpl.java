package com.onlineBookStore.onlineBookStore.implementations;

import com.onlineBookStore.onlineBookStore.entities.Book;
import com.onlineBookStore.onlineBookStore.entities.User;
import com.onlineBookStore.onlineBookStore.repositories.BookRepository;
import com.onlineBookStore.onlineBookStore.repositories.UserRepository;
import com.onlineBookStore.onlineBookStore.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public boolean addBookToCart(int userId, int bookId) {
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

        user.getCart().add(book);
        userRepository.save(user);
        System.out.println("Book added to cart successfully.");
        return true;
    }

    @Override
    @Transactional
    public boolean removeBookFromCart(int userId, int bookId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getCart().removeIf(book -> book.getId() == bookId);
        }
        return false;
    }

    @Override
    @Transactional
    public List<Book> getBooksInCart(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return List.of(); // Return empty list if user is not found
        }

        List<Book> books = user.getCart();
        if (books == null || books.isEmpty()) {
            System.out.println("No books found for this user.");
        }
        return books != null ? books : List.of(); // Ensure we return a non-null list
    }
}
