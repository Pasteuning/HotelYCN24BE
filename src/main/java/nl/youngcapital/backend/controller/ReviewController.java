package nl.youngcapital.backend.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.service.HotelService;
import nl.youngcapital.backend.service.ReviewService;
import nl.youngcapital.backend.service.ReviewService.Status;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HotelService hotelService;


    // Create
//    @PostMapping("/create-review/{hotelId}/{accountId}")
//    public ReviewService.Status createReview(@PathVariable ("hotelId") long hotelId, @PathVariable ("accountId") long accountId, @RequestBody Review review) {
//        return reviewService.createReview(hotelId, accountId, review);
//    }

    @PostMapping("/create-review/{hotelId}")
    public ReviewService.Status createReview(@PathVariable ("hotelId") long hotelId, @RequestBody Review review, HttpServletRequest request) {
    	Account account = (Account)request.getAttribute("YC_ACCOUNT");
    	if (account == null) {
    		return null;
    	}
    	
    	Optional<Hotel> hotelOptional = hotelService.getHotel(hotelId);
    	if (hotelOptional.isEmpty()) {
    		return null;
    	}
    	
    	if (review.getRating() > 5) {
	    	System.err.println("Rating cannot be more than 5 stars");
	    	return Status.TOO_MANY_STARS;
	    } else if (review.getComment().length() > 1000){
	    	System.err.println("Comment cannot contain more than 1000 characters");
	    	return Status.TOO_MANY_CHARACTERS;
	    }
    	
    	Review dbReview = new Review();
    	dbReview.setComment(review.getComment());
    	dbReview.setName(review.getName());
    	dbReview.setAccount(account);
    	dbReview.setHotel(hotelOptional.get());
    	dbReview.setRating(review.getRating());
    	dbReview.setDate(LocalDateTime.now());

        reviewService.createReview(review);

        return ReviewService.Status.SUCCESS;
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
