package com.fujitsu.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionalBaseFeeDto {
    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "City cannot be blank")
    private String vehicleType;

    @NotNull(message = "Fee cannot be null")
    @Min(value = 0, message = "Fee cannot be negative")
    private Double fee;
}
