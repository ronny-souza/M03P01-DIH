package tech.devinhouse.aviation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.aviation.service.SeatService;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public ResponseEntity<Page<String>> listSeats(@PageableDefault(size = 12) Pageable pagination) {
        Page<String> response = this.seatService.listSeats(pagination);
        return ResponseEntity.ok(response);
    }
}
