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
@NoArgsConstructor
@Getter
@Setter
@ToString
@NamedQueries({
    @NamedQuery(name="Harbour.deleteAll", query="DELETE FROM Harbour")
})
public class Harbour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "zip", nullable = false)
    private int zip;
    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "harbour", fetch = FetchType.EAGER)
    @ToString.Exclude
    private final Set<Boat> boats = new HashSet<>();

    public Harbour(String street, String name, int zip, String city) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.name = name;
    }
}