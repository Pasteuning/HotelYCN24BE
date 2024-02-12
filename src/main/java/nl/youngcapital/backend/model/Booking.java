package nl.youngcapital.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    public enum PaymentMethod { CASH, IDEAL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private LocalDateTime date;
    @OneToOne
    private Reservation reservation;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonIgnore
    public Reservation getReservation() {
        return reservation;
    }

    @JsonIgnore
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
