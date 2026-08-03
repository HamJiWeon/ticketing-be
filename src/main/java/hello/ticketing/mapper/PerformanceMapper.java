package hello.ticketing.mapper;

import hello.ticketing.domain.Performance;
import hello.ticketing.dto.response.PerformanceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformanceMapper {

    PerformanceResponse toDto(Performance performance);
}
