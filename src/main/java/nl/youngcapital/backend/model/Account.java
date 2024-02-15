package nl.youngcapital.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
    public enum Role { GUEST, STAFF, OWNER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false)
    private int loyaltyPoints;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private long hotelId; // -100 (default) = user, 0 = owner, 1+ = staff of hotel ${hotelId}
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "account")
    private List<Review> reviews = new ArrayList<>();
    
    @Column(length = 100, unique = true)
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> review) {
        this.reviews = review;
    }
    
    public String getToken() {
		return token;
	}
    
    public void setToken(String token) {
		this.token = token;
	}
}
