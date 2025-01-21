package com.example.simbirsoft;

import com.example.simbirsoft.dto.CreateTicketDto;
import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.AirplaneRepository;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import com.example.simbirsoft.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirplaneRepository airplaneRepository;

    @InjectMocks
    private TicketService ticketService;

    private CreateTicketDto createTicketDto;
    private Flight flight;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        createTicketDto = new CreateTicketDto();
        createTicketDto.setPrice(BigDecimal.valueOf(1000));
        createTicketDto.setStatus(TicketStatus.BOOKED);
        createTicketDto.setCommission(false);
        createTicketDto.setFlightId(1L);

        flight = new Flight();
        flight.setId(1L);
        flight.setStartingPoint("Kazan");

        ticket = new Ticket();
        ticket.setPrice(BigDecimal.valueOf(1000));
        ticket.setStatus(TicketStatus.BOOKED);
    }

    @Test
    void testCreateTicket() {
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        CreateTicketDto result = ticketService.createTicket(createTicketDto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1000), result.getPrice());
        assertEquals(TicketStatus.BOOKED, result.getStatus());

        verify(flightRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void testUpdateTicketById() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDto updateDto = new TicketDto();
        updateDto.setPrice(BigDecimal.valueOf(1500));
        updateDto.setStatus(TicketStatus.SOLD);

        TicketDto result = ticketService.updateTicketById(1L, updateDto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1500), result.getPrice());
        assertEquals(TicketStatus.SOLD, result.getStatus());

        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

}
