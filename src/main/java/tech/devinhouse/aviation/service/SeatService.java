package tech.devinhouse.aviation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.aviation.exception.PassengerNotLegalAgeForEmergencySeatException;
import tech.devinhouse.aviation.exception.SeatNotFoundException;
import tech.devinhouse.aviation.exception.UncheckedBagsException;
import tech.devinhouse.aviation.model.Seat;
import tech.devinhouse.aviation.repository.SeatRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class SeatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatService.class);

    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Page<String> listSeats(Pageable pagination) {
        LOGGER.info("Searching for all seats...");
        return this.seatRepository.findAll(pagination).map(Seat::getName);
    }

    public Seat findSeatByName(String name) {
        String nameUpperCase = name.toUpperCase();
        LOGGER.info("Searching for seat {}", nameUpperCase);

        Optional<Seat> optionalSeat = this.seatRepository.findByName(name);

        if (optionalSeat.isPresent()) {
            LOGGER.info("Returning seat found");
            return optionalSeat.get();
        }
        throw new SeatNotFoundException(String.format("Seat %s not found", nameUpperCase));
    }

    @Transactional
    public void reserveSeat(Seat seat) {
        seat.reserveSeat();
    }

    public void checkPassengerInLegalAgeToEmergencySeat(Seat seat, LocalDate birthDate) {
        LOGGER.info("Checking if the seat is part of the emergency row and if the passenger is of legal age...");
        Period period = Period.between(birthDate, LocalDate.now());
        boolean isUnderAge = period.getYears() < 18;

        if (seat.isEmergency() && isUnderAge) {
            throw new PassengerNotLegalAgeForEmergencySeatException("The passenger is not of legal age for an emergency seat. He must possess 18 years or more");
        }
    }

    public void checkBagsHaveBeenCheckedInEmergencySeat(Seat seat, Boolean isCheckedBags) {
        LOGGER.info("Checking that bags have been dispatched to emergency row seats...");
        if (seat.isEmergency() && !isCheckedBags) {
            throw new UncheckedBagsException(String.format("Your seat %s is in the emergency row and, therefore, your baggage must be checked in", seat.getName()));
        }
    }
}
