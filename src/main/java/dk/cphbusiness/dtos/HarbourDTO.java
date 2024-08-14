package dk.cphbusiness.dtos;

import dk.cphbusiness.persistence.model.Harbour;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose of this class is to
 * Author: Thomas Hartmann
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HarbourDTO {
    private String id;
    private String street;
    private String city;
    private int zipCode;
    private Integer[] residents;

    public HarbourDTO(Harbour harbour) {
        this.id = harbour.getId().toString();
        this.street = harbour.getStreet();
        this.city = harbour.getCity();
        this.zipCode = harbour.getZip();
        if(harbour.getBoats()!=null)
            this.residents = harbour.getBoats().stream().map(person -> person.getId()).toArray(Integer[]::new);
    }

    public Harbour getEntity() {
        Harbour harbour = new Harbour();
        if(id!=null)
            harbour.setId(Integer.parseInt(id));
        harbour.setStreet(street);
        harbour.setZip(zipCode);
        harbour.setCity(city);
        return harbour;
    }

    public static Set<HarbourDTO> getEntities(Set<Harbour> harbours) {
        return harbours.stream().map(address -> new HarbourDTO(address)).collect(Collectors.toSet());
    }
}
