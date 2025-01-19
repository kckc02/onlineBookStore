package com.onlineBookStore.onlineBookStore.repositories;

import com.onlineBookStore.onlineBookStore.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBookId(int bookId);
}
