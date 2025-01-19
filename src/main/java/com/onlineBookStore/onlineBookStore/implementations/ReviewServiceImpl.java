package com.onlineBookStore.onlineBookStore.implementations;

import com.onlineBookStore.onlineBookStore.entities.Review;
import com.onlineBookStore.onlineBookStore.repositories.ReviewRepository;
import com.onlineBookStore.onlineBookStore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(int userId, int bookId, int rating, String comment) {
        Review review = new Review(userId, bookId, rating, comment);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForBook(int bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
