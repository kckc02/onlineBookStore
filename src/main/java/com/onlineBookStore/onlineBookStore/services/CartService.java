package com.onlineBookStore.onlineBookStore.services;

import com.onlineBookStore.onlineBookStore.entities.Book;

import java.util.List;

public interface CartService {
    boolean addBookToCart(int userId, int bookId);

    boolean removeBookFromCart(int userId, int bookId);

    List<Book> getBooksInCart(int userId);
}
