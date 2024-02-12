package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReviewController {
    @Autowired
    private ReviewService reviewService;



    // Create
    @PostMapping("/create-review/{hotelId}/{accountId}")
    public boolean createReview(@PathVariable ("hotelId") long hotelId, @PathVariable ("accountId") long accountId, @RequestBody Review review) {
        return reviewService.createReview(hotelId, accountId, review);
    }


    // Read
    @GetMapping("/all-reviews")
    public Iterable<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/review/{id}")
    public Optional<Review> getReview(@PathVariable("id") long id) {
        return reviewService.getReview(id);
    }


    // Edit
    @PutMapping("/edit-review/{id}")
    public boolean editReview(@PathVariable ("id") long id, @RequestBody Review updatedReview) {
        return reviewService.editReview(id, updatedReview);
    }


    // Delete
    @DeleteMapping("/delete-review/{id}")
    public boolean deleteReview(@PathVariable ("id") long id) {
        return reviewService.deleteReview(id);
    }
}
