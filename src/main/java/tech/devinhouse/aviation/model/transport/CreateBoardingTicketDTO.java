package tech.devinhouse.aviation.model.transport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBoardingTicketDTO(@NotBlank String cpf, @NotBlank String seat, @NotNull Boolean checkedBags) {
}
