package tech.devinhouse.aviation.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import tech.devinhouse.aviation.model.Passenger;
import tech.devinhouse.aviation.model.enums.ClassificationEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record ListPassengerDTO(String cpf, String name, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthDate,
                               ClassificationEnum classification,
                               Integer miles, List<BoardingTicketDTO> tickets) {

    public ListPassengerDTO(Passenger passenger) {
        this(passenger.getCpf(),
                passenger.getName(),
                passenger.getBirthDate(),
                passenger.getClassification(),
                passenger.getMiles(),
                passenger.getTickets().stream().map(BoardingTicketDTO::new).collect(Collectors.toList()));
    }
}
