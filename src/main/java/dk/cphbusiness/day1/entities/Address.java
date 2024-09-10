package dk.cphbusiness.day1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
//@Builder
@ToString
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String street;
    private int number;
    private int zip;
    private String city;

    public Address(String street, int number, int zip, String city){
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.city = city;
    }
    @ManyToMany
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons = new HashSet<>();

//    public void addPerson(Person p){
//        if(this.persons == null){
//            this.persons = new HashSet();
//        }
//        if(!this.persons.contains(p)) {
//            this.persons.add(p);
//        }
//        p.addAddress(this);
//    }
//    public void removePerson(Person p){
//        if(this.persons.contains(p))
//            this.persons.remove(p);
//        p.removeAddress(this);
//    }

}
