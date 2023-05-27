package tech.devinhouse.aviation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.aviation.exception.PassengerNotFoundException;
import tech.devinhouse.aviation.exception.SeatAlreadyReservedException;
import tech.devinhouse.aviation.model.Passenger;
import tech.devinhouse.aviation.model.Seat;
import tech.devinhouse.aviation.model.transport.BoardingTicketDTO;
import tech.devinhouse.aviation.model.transport.CreateBoardingTicketDTO;
import tech.devinhouse.aviation.model.transport.ListPassengerDTO;
import tech.devinhouse.aviation.model.transport.PassengerDTO;
import tech.devinhouse.aviation.repository.PassengerRepository;

import java.util.Optional;

@Service
public class PassengerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);

    private PassengerRepository passengerRepository;

    private SeatService seatService;

    private BoardingTicketService boardingTicketService;

    public PassengerService(PassengerRepository passengerRepository, SeatService seatService, BoardingTicketService boardingTicketService) {
        this.passengerRepository = passengerRepository;
        this.seatService = seatService;
        this.boardingTicketService = boardingTicketService;
    }

    public Page<ListPassengerDTO> listPassengers(Pageable pagination) {
        return this.passengerRepository.findAll(pagination).map(ListPassengerDTO::new);
    }

    public PassengerDTO findByCpf(String cpf) {
        LOGGER.info("Searching for user {}...", cpf);
        Optional<Passenger> optionalPassenger = this.passengerRepository.findByCpf(cpf);

        if (optionalPassenger.isPresent()) {
            LOGGER.info("Returning user found...");
            return new PassengerDTO(optionalPassenger.get());
        }
        throw new PassengerNotFoundException(String.format("Passenger with CPF %s is not found", cpf));
    }

    private Passenger findPersistentByCpf(String cpf) {
        LOGGER.info("Searching for user {}...", cpf);
        Optional<Passenger> optionalPassenger = this.passengerRepository.findByCpf(cpf);

        if (optionalPassenger.isPresent()) {
            LOGGER.info("Returning user found...");
            return optionalPassenger.get();
        }
        throw new PassengerNotFoundException(String.format("Passenger with CPF %s is not found", cpf));
    }

    @Transactional
    public void recalculateMiles(String cpf) {
        LOGGER.info("Recalculating passenger miles...");
        Passenger passenger = this.passengerRepository.getReferenceByCpf(cpf);
        passenger.plusMiles();
    }

    @Transactional
    public BoardingTicketDTO confirmCheckin(CreateBoardingTicketDTO createBoardingTicketDTO) {
        Passenger passenger = this.findPersistentByCpf(createBoardingTicketDTO.cpf());
        Seat seat = this.seatService.findSeatByName(createBoardingTicketDTO.seat());

        LOGGER.info("Checking if the chosen seat is available...");
        if (!seat.isAvailable()) {
            throw new SeatAlreadyReservedException(String.format("The chosen seat has already been reserved by another passenger: %s", seat.getName()));
        }

        PassengerDTO passengerDTO = new PassengerDTO(passenger);
        this.seatService.checkPassengerInLegalAgeToEmergencySeat(seat, passengerDTO.birthDate());
        this.seatService.checkBagsHaveBeenCheckedInEmergencySeat(seat, createBoardingTicketDTO.checkedBags());

        BoardingTicketDTO boardingTicketDTO = this.boardingTicketService.create(createBoardingTicketDTO, passenger);
        this.recalculateMiles(passengerDTO.cpf());
        this.seatService.reserveSeat(seat);
        LOGGER.info("Confirmation made by passenger {} with E-Ticket: {}", passenger.getCpf(), boardingTicketDTO.eTicket());
        return boardingTicketDTO;
    }
}
