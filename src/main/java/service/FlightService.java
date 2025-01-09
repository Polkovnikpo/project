package service;

import dto.FlightDto;
import entity.Airplane;
import entity.Flight;
import org.springframework.stereotype.Service;
import repository.FlightRepository;

import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDto createFlight(FlightDto dto) {
        Flight flight = mapDtoToFlight(dto);
        flightRepository.save(flight);
        FlightDto flightDto = mapFlightToDto(flight);
        return flightDto;
    }

    public FlightDto updateFlight(Long id, FlightDto dto) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            flight.setDepartureTime(dto.getDepartureTime());
            flight.setArrivalTime(dto.getArrivalTime());
            flight.setStartingPoint(dto.getStartingPoint());
            flight.setDestinationPoint(dto.getDestinationPoint());

            flightRepository.save(flight);

            FlightDto flightDto = mapFlightToDto(flight);
            return flightDto;
        }
        return null;
    }

    public FlightDto getFlightById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            FlightDto flightDto = mapFlightToDto(flightOptional.get());
            return flightDto;
        }
        return null;
    }

    public void deleteFlightDto(Long id){
        flightRepository.deleteById(id);
    }

    public Flight mapDtoToFlight(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setStartingPoint(flightDto.getStartingPoint());
        flight.setDestinationPoint(flightDto.getDestinationPoint());
        return flight;
    }

    public FlightDto mapFlightToDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setArrivalTime(flight.getArrivalTime());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setStartingPoint(flight.getStartingPoint());
        flightDto.setDestinationPoint(flight.getDestinationPoint());
        return flightDto;
    }
}
