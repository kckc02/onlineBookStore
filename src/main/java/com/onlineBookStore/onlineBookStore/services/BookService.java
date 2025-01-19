package com.onlineBookStore.onlineBookStore.services;

import com.onlineBookStore.onlineBookStore.entities.Book;

import java.util.List;

public interface BookService {

    // Get all books
    List<Book> getAllBooks();

    // Get book by ID
    Book getBookById(int bookId);

    // Purchase book
    boolean purchaseBook(int userId, int bookId, double paymentAmount);

    // Get books by user
    List<Book> getBooksByUser(int userId);

    // Add a book
    void addBook(String title, String author, double price, int quantity);

    // Add multiple books
    void addBooks();

    // Edit a book
    boolean editBook(int bookId, String newTitle, String newAuthor, Double newPrice, Integer newQuantity);

    // Delete a book
    boolean deleteBook(int bookId);
}
