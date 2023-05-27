package tech.devinhouse.aviation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.aviation.model.BoardingTicket;
import tech.devinhouse.aviation.model.Passenger;
import tech.devinhouse.aviation.model.transport.BoardingTicketDTO;
import tech.devinhouse.aviation.model.transport.CreateBoardingTicketDTO;
import tech.devinhouse.aviation.repository.BoardingTicketRepository;

@Service
public class BoardingTicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardingTicketService.class);

    private BoardingTicketRepository boardingTicketRepository;

    public BoardingTicketService(BoardingTicketRepository boardingTicketRepository) {
        this.boardingTicketRepository = boardingTicketRepository;
    }

    @Transactional
    public BoardingTicketDTO create(CreateBoardingTicketDTO createBoardingTicketDTO, Passenger passenger) {
        LOGGER.info("Confirming and generating the E-Ticket...");
        BoardingTicket boardingTicket = new BoardingTicket(createBoardingTicketDTO, passenger);
        this.boardingTicketRepository.save(boardingTicket);
        return new BoardingTicketDTO(boardingTicket);
    }
}
