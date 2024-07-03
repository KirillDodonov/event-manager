package ru.dodonov.eventmanager.locations;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationDto (
        Long id,
        @NotBlank(message = "Name must be not empty")
        String name,
        @NotBlank(message = "Address must be not empty")
        String address,
        @NotNull
        @Min(value = 5, message = "Minimum capacity of location is 5")
        Long capacity,
        String description
) {
}
