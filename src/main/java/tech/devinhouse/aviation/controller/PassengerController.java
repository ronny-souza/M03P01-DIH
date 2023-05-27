package tech.devinhouse.aviation.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.aviation.model.transport.BoardingTicketDTO;
import tech.devinhouse.aviation.model.transport.CreateBoardingTicketDTO;
import tech.devinhouse.aviation.model.transport.ListPassengerDTO;
import tech.devinhouse.aviation.model.transport.PassengerDTO;
import tech.devinhouse.aviation.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public ResponseEntity<Page<ListPassengerDTO>> listPassengers(@PageableDefault(size = 12, sort = "name") Pageable pagination) {
        Page<ListPassengerDTO> response = this.passengerService.listPassengers(pagination);

        if (!response.hasContent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<PassengerDTO> findByCpf(@PathVariable("cpf") final String cpf) {
        PassengerDTO response = this.passengerService.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirmation")
    public ResponseEntity<BoardingTicketDTO> confirmCheckin(@RequestBody @Valid CreateBoardingTicketDTO createBoardingTicketDTO) {
        BoardingTicketDTO response = this.passengerService.confirmCheckin(createBoardingTicketDTO);
        return ResponseEntity.ok(response);
    }
}
