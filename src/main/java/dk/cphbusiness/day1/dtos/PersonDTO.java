package dk.cphbusiness.day1.dtos;

import dk.cphbusiness.day1.entities.Person;
import lombok.*;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String name;
    private String email;

    public PersonDTO(Person person){
        this.name = person.getName();
        this.email = person.getEmail();
    }
    public PersonDTO(String name){
        this.name = name;
    }
}
