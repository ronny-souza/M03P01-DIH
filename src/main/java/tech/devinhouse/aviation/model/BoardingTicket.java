package tech.devinhouse.aviation.model;

import jakarta.persistence.*;
import lombok.*;
import tech.devinhouse.aviation.model.transport.CreateBoardingTicketDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "boardingTicket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eTicket;

    @Column(nullable = false)
    private String seat;

    @Column(columnDefinition = "tinyint DEFAULT 0")
    private boolean checkedBags;

    private LocalDateTime confirmationDate;

    @ManyToOne
    private Passenger passenger;

    public BoardingTicket(CreateBoardingTicketDTO boardingTicketDTO, Passenger passenger) {
        this.setETicket(UUID.randomUUID().toString());
        this.setSeat(boardingTicketDTO.seat());
        this.setCheckedBags(boardingTicketDTO.checkedBags());
        this.setConfirmationDate(LocalDateTime.now());
        this.setPassenger(passenger);
    }
}
