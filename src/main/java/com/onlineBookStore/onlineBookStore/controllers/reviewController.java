package com.onlineBookStore.onlineBookStore.controllers;

import com.onlineBookStore.onlineBookStore.entities.Review;
import com.onlineBookStore.onlineBookStore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class reviewController {

    @Autowired
    private ReviewService reviewService;

    // Endpoint to add a review for a book
    @PostMapping("/add")
    public ResponseEntity<String> addReview(
            @RequestParam int userId,
            @RequestParam int bookId,
            @RequestParam int rating,
            @RequestParam String comment) {

        reviewService.addReview(userId, bookId, rating, comment); // Calls ReviewService's method
        return ResponseEntity.ok("Review added successfully.");
    }

    // Endpoint to get reviews for a specific book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsForBook(@PathVariable int bookId) {
        List<Review> reviews = reviewService.getReviewsForBook(bookId); // Calls ReviewService's method
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no reviews are found
        }
        return ResponseEntity.ok(reviews); // Return 200 with the list of reviews
    }
}
