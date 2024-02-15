package nl.youngcapital.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
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
//    @PostMapping("/create-review/{hotelId}/{accountId}")
//    public ReviewService.Status createReview(@PathVariable ("hotelId") long hotelId, @PathVariable ("accountId") long accountId, @RequestBody Review review) {
//        return reviewService.createReview(hotelId, accountId, review);
//    }

    @PostMapping("/create-review/{hotelId}/")
    public ReviewService.Status createReview(@PathVariable ("hotelId") long hotelId, @RequestBody Review review, HttpServletRequest request) {
        return reviewService.createReview(hotelId, review, request);
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
    public ReviewService.Status editReview(@PathVariable ("id") long id, @RequestBody Review updatedReview) {
        return reviewService.editReview(id, updatedReview);
    }


    // Delete
    @DeleteMapping("/delete-review/{id}")
    public ReviewService.Status deleteReview(@PathVariable ("id") long id) {
        return reviewService.deleteReview(id);
    }
}
