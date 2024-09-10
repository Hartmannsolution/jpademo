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
@Builder
@ToString
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String password;
//    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
//    private Set<Address> addresses = new HashSet<>();

    public Person(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
//    public void addAddress(Address a){
//        this.addresses.add(a);
//        if(!a.getPersons().contains(this)) {
//            a.addPerson(this);
//        }
//    }
//    public void removeAddress(Address a){
//        this.addresses.remove(a);
//        a.removePerson(this);
//    }

}
