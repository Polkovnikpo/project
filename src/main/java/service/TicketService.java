package service;

import dto.FlightDto;
import dto.TicketDto;
import entity.Flight;
import entity.Ticket;
import entity.TicketStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TicketRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = mapDtoToTicket(ticketDto);
        ticketRepository.save(ticket);
        TicketDto dto = mapTicketToDto(ticket);

        return dto;
    }

    public TicketDto createTicketWithCommission(TicketDto dto, BigDecimal commissionRite) {
        Ticket ticket = mapDtoToTicket(dto);
        int commissionPrice = calculateCommission(ticket.getPrice(),commissionRite);
        ticket.setPrice(BigDecimal.valueOf(commissionPrice));
        ticketRepository.save(ticket);
        return mapTicketToDto(ticket);
    }

    private int calculateCommission(BigDecimal basePrice,BigDecimal commissionRite){
        BigDecimal commission = basePrice.multiply(commissionRite.divide(BigDecimal.valueOf(100)));
        return basePrice.add(commission).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public TicketDto updateTicketById(Long id, TicketDto dto) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = new Ticket();
            ticket.setPrice(dto.getPrice());
            ticket.setStatus(dto.getStatus());

            ticketRepository.save(ticket);

            TicketDto ticketDto = mapTicketToDto(ticket);
            return ticketDto;
        }
        return null;
    }

    public TicketDto getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            TicketDto ticketDto = mapTicketToDto(ticketOptional.get());
            return ticketDto;
        }
        return null;
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket mapDtoToTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setPrice(ticketDto.getPrice());
        ticket.setStatus(ticketDto.getStatus());
        return ticket;
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        return ticketDto;
    }
}
