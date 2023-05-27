package tech.devinhouse.aviation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.aviation.model.Passenger;
import tech.devinhouse.aviation.model.transport.PassengerDTO;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByCpf(String cpf);

    Passenger getReferenceByCpf(String cpf);
}
