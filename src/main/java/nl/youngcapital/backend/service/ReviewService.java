package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.AccountRepository;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.ReviewRepository;
import nl.youngcapital.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AccountRepository accountRepository;


    
    // Create
    public boolean createReview(long hotelId, long accountId, Review review) {
        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
            Account account = accountRepository.findById(accountId).orElseThrow();

            review.setDate(LocalDateTime.now());
            review.setAccount(account);
            review.setHotel(hotel);

            reviewRepository.save(review);
            System.out.println("Successfully created review on Id: " + review.getId());
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to create review. " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            System.err.println("Failed to save review to the database: " + e.getMessage());
            return false;
        }
    }


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
    public boolean editReview(long id, Review updatedReview) {
        try {
            Review review = reviewRepository.findById(id).orElseThrow();
            review.setComment(updatedReview.getComment());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            System.out.println("Successfully updated review with Id: " + id);
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to edit password. Cannot find review on Id: " + id);
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to edit review with Id: " + id);
            System.err.println(e.getMessage());
        }
        return false;
    }


    // Delete
    public boolean deleteReview(long id) {
        if (reviewRepository.findById(id).isPresent()) {
            try {
                reviewRepository.deleteById(id);
                System.out.println("Successfully deleted review with Id: " + id);
                return true;
            } catch (Exception e) {
                System.err.println("Failed to delete review on Id: " + id);
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Failed to delete review. Cannot find review on Id: " + id);
        }
        return false;
    }


}
