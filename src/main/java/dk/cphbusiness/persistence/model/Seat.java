package dk.cphbusiness.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * Purpose of this class is to
 * Author: Thomas Hartmann
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "boat") // Avoid infinite recursion
public class Seat {

    @Id
    @Column(name = "phonenumber", nullable = false)
    private String number;

    private String description;

    @ManyToOne
    private Boat boat;

    public String getId() {
        return number;
    }

    @Builder
    public Seat(String number, String description) {
        this.number = number;
        this.description = description;
    }

}