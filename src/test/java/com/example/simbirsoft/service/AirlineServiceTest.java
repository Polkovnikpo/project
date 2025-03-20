package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.entity.Airline;
import com.example.simbirsoft.entity.Airplane;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.repository.AirlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineService airlineService;

    private Airline airline;

    @BeforeEach
    void setUp() {
        airline = new Airline();
        airline.setId(1L);
        airline.setName("Test Airline");
    }

    @Test
    void testGetTicketsCountByAirlineId() {
        Airplane airplane = new Airplane();
        Flight flight = new Flight();
        flight.setTickets(List.of(new Ticket(), new Ticket()));
        airplane.setFlights(List.of(flight));
        airline.setAirplanes(List.of(airplane));

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));

        int ticketCount = airlineService.getTicketsCountByAirlineId(1L);

        assertEquals(2, ticketCount);
        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTicketsCountByAirlineId_NotFound() {
        when(airlineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> airlineService.getTicketsCountByAirlineId(1L));
        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTicketsCountByAirlineId_NoAirplanesOrFlights() {
        airline.setAirplanes(Collections.emptyList());
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));

        int ticketCount = airlineService.getTicketsCountByAirlineId(1L);
        assertEquals(0, ticketCount);
    }

    @Test
    void testCreateAirline() {
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName("New Airline");

        when(airlineRepository.save(any(Airline.class))).thenAnswer(invocation -> {
            Airline savedAirline = invocation.getArgument(0);
            savedAirline.setId(1L);
            return savedAirline;
        });

        AirlineDto createdAirline = airlineService.createAirline(airlineDto);

        assertNotNull(createdAirline);
        assertEquals("New Airline", createdAirline.getName());
        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void testUpdateAirline() {
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName("New Airline");

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(airlineRepository.save(any(Airline.class))).thenReturn(airline);

        AirlineDto updatedAirline = airlineService.updateAirline(1L, airlineDto);

        assertNotNull(updatedAirline);
        assertEquals("New Airline", updatedAirline.getName());
        verify(airlineRepository, times(1)).findById(1L);
        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void testUpdateAirline_NotFound() {
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName("New Airline");

        when(airlineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> airlineService.updateAirline(1L, airlineDto));
        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAirlineById() {
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));

        AirlineDto airlineDto = airlineService.getAirlineById(1L);

        assertNotNull(airlineDto);
        assertEquals("Test Airline", airlineDto.getName());
        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAirlineById_NotFound() {
        when(airlineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> airlineService.getAirlineById(1L));
        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteAirline() {
        doNothing().when(airlineRepository).deleteById(1L);

        airlineService.deleteAirlineById(1L);

        verify(airlineRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAirline_NotFound() {
        doThrow(new RuntimeException("Airline not found"))
                .when(airlineRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> airlineService.deleteAirlineById(1L));
        verify(airlineRepository, times(1)).deleteById(1L);
    }
}