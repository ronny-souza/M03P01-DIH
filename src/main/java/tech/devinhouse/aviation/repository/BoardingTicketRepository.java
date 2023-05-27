package tech.devinhouse.aviation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.aviation.model.BoardingTicket;

@Repository
public interface BoardingTicketRepository extends JpaRepository<BoardingTicket, Long> {
}
