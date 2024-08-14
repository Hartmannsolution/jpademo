package dk.cphbusiness.persistence.model;

import jakarta.persistence.*;
import lombok.*;

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