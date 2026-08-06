package hello.ticketing.mapper;

import hello.ticketing.domain.Reservation;
import hello.ticketing.dto.response.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "round.id", target = "roundId")
    @Mapping(source = "round.performance.name", target = "performanceName")
    ReservationResponse toDto(Reservation reservation);
}
