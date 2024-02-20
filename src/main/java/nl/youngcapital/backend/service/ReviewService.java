package nl.youngcapital.backend.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.repository.AccountRepository;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AccountRepository accountRepository;

    public enum Status { SUCCESS, FAILED, INVALID_INPUT }

    
    // Create
    public void createReview(Review review) {
    	reviewRepository.save(review);
    }
    	
    	// welk user is er

//        if (review.getRating() > 5) {
//            System.err.println("Rating cannot be more than 5 stars");
//            return Status.TOO_MANY_STARS;
//        } else if (review.getComment().length() > 1000){
//            System.err.println("Comment cannot contain more than 1000 characters");
//            return Status.TOO_MANY_CHARACTERS;
//        }
//
//        try {
//            Hotel hotel = hotelRepository.findById(hotelId)
//                    .orElseThrow(() -> new NoSuchElementException("Cannot find hotel with Id: " + hotelId));
//            Account account = accountRepository.findById(sessionDTO.getAccountId())
//                    .orElseThrow(() -> new NoSuchElementException("Cannot find account with Id: " + sessionDTO.getAccountId()));
//
//            review.setDate(LocalDateTime.now());
//            review.setAccount(account);
//            review.setHotel(hotel);
//
//            reviewRepository.save(review);
//            System.out.println("Successfully created review on Id: " + review.getId());
//            return Status.SUCCESS;
//        } catch (NoSuchElementException e) {
//            System.err.println("Failed to create review. " + e.getMessage());
//            return Status.FAILED;
//        } catch (DataAccessException e) {
//            System.err.println("Failed to save review to the database: " + e.getMessage());
//            return Status.FAILED;
//        }


    // Read
    public Iterable<Review> getAllReviews() {
        System.out.println("Returning list of all reviews");
        return reviewRepository.findAll();
    }

    public Optional<Review> getReview(long id) {
        if (reviewRepository.findById(id).isPresent()) {
            System.out.println("Returning review on Id: " + id);
        } else {
            System.err.println("Failed to get review. Cannot find review on Id: " + id);
        }
        return reviewRepository.findById(id);
    }


    // Edit
    public Status editReview(long id, Review updatedReview) {
        try {
            Review review = reviewRepository.findById(id).orElseThrow();
            review.setComment(updatedReview.getComment());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            System.out.println("Successfully updated review with Id: " + id);
            return Status.SUCCESS;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to edit password. Cannot find review on Id: " + id);
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to edit review with Id: " + id);
            System.err.println(e.getMessage());
        }
        return Status.FAILED;
    }


    // Delete
    public Status deleteReview(long id) {
        if (reviewRepository.findById(id).isPresent()) {
            try {
                reviewRepository.deleteById(id);
                System.out.println("Successfully deleted review with Id: " + id);
                return Status.SUCCESS;
            } catch (Exception e) {
                System.err.println("Failed to delete review on Id: " + id);
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Failed to delete review. Cannot find review on Id: " + id);
        }
        return Status.FAILED;
    }


}
