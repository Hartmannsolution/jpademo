package dk.cphbusiness.dtos;

import dk.cphbusiness.persistence.model.Boat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose of this class is to represent a Boat entity
 * Author: Thomas Hartmann
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoatDTO {
    private String id;
    private String brand;
    private String model;
    private String name;
    private Set<SeatDTO> seats;
    private LocalDate creationDate;
    private String harbour;
    private Set<String> owners;

    public BoatDTO(String brand, String model, String name, Set<SeatDTO> seats, LocalDate creationDate, String harbour, Set<String> owners) {
        this.brand = brand;
        this.model = model;
        this.name = name;
        this.seats = seats;
        this.creationDate = creationDate;
        this.harbour = harbour;
        this.owners = owners;
    }
    public BoatDTO(Boat boat) {
        if(boat.getId()!=null)
            this.id = boat.getId().toString();
        this.brand = boat.getBrand();
        this.model = boat.getModel();
        this.name = boat.getName();
        this.seats = SeatDTO.getSeatDTOs(boat.getSeats());
        this.creationDate = boat.getCreationDate();
        if(boat.getHarbour()!=null)
            this.harbour = boat.getHarbour().getId().toString();
        this.owners = boat.getOwners().stream().map(person -> person.getId().toString()).collect(Collectors.toSet());
    }

    public void setId(String id) {
        this.id = id;
    }
    public Boat toEntity() {
        Boat boat = Boat.builder()

                .build();
        if(id!=null)
            boat.setId(Integer.parseInt(id));
        return boat;
    }
    public static Set<BoatDTO> getEntities(Set<Boat> boats) {
        return boats.stream().map(person -> new BoatDTO(person)).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "BoatDTO{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", harbour='" + harbour + '\'' +
                ", owners=" + owners +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        if(obj.getClass()!=this.getClass())
            return false;
        BoatDTO other = (BoatDTO) obj;
        return this.id.equals(other.id)
                && this.brand.equals(other.brand)
                && this.model.equals(other.model)
                && this.name.equals(other.name)
                && this.creationDate.equals(other.creationDate);
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
