package tech.devinhouse.aviation.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import tech.devinhouse.aviation.model.BoardingTicket;

import java.time.LocalDateTime;

public record BoardingTicketDTO(String eTicket, String seat, @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime confirmationDate) {

    public BoardingTicketDTO(BoardingTicket boardingTicket) {
        this(boardingTicket.getETicket(), boardingTicket.getSeat(), boardingTicket.getConfirmationDate());
    }
}
