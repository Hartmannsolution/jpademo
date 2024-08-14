package dk.cphbusiness.dtos;

import dk.cphbusiness.persistence.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Purpose of this class is to
 * Author: Thomas Hartmann
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatDTO {
    String number;
    String description;
    public SeatDTO(Seat seat) {
        this.number = seat.getNumber();
        this.description = seat.getDescription();
    }
    public Seat toEntity() {
        return Seat.builder()
                .number(number)
                .description(description)
                .build();
    }
    public Set<Seat> getEntities(Set<SeatDTO> seats) {
        return seats.stream().map(SeatDTO::toEntity).collect(java.util.stream.Collectors.toSet());
    }
    public static Set<SeatDTO> getSeatDTOs(Set<Seat> seats) {
        return seats.stream().map(SeatDTO::new).collect(java.util.stream.Collectors.toSet());
    }
}
