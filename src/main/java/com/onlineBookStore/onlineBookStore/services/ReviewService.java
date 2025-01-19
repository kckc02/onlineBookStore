package com.onlineBookStore.onlineBookStore.services;

import com.onlineBookStore.onlineBookStore.entities.Review;

import java.util.List;

public interface ReviewService {
    void addReview(int userId, int bookId, int rating, String comment);

    List<Review> getReviewsForBook(int bookId);
}
