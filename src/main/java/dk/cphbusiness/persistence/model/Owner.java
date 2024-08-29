package dk.cphbusiness.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Purpose of this class is to
 * Author: Thomas Hartmann
 */
@Entity
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private OwnerCategory category;

    @ManyToMany(mappedBy = "owners")
    @ToString.Exclude
    private final Set<Boat> boats = new HashSet<>();

    public String getId() {
        return name;
    }

    @Builder
    public Owner(String name, OwnerCategory category ) {
        this.name = name;
        this.category = category;
    }


    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (!validatePhoneNumber(this.phoneNumber)) {
            throw new IllegalArgumentException("Phone number could not be validated");
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (!validatePhoneNumber(this.phoneNumber)) {
            throw new IllegalArgumentException("Phone number could not be validated");
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return true;
        }

        return phoneNumber.matches("^[0-9]{8,11}$");
    }

    @Getter
    public enum OwnerCategory {
        COMPANY("Company"),
        PRIVATE("Private"),
        PUBLIC("Public");

        private final String name;

        OwnerCategory(String name) {
            this.name = name;
        }
    }

}