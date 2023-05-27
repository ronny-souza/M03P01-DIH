package tech.devinhouse.aviation.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import tech.devinhouse.aviation.model.Passenger;
import tech.devinhouse.aviation.model.enums.ClassificationEnum;

import java.time.LocalDate;

public record PassengerDTO(String cpf, String name, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthDate,
                           ClassificationEnum classification, Integer miles) {

    public PassengerDTO(Passenger passenger) {
        this(passenger.getCpf(), passenger.getName(), passenger.getBirthDate(), passenger.getClassification(), passenger.getMiles());
    }
}
