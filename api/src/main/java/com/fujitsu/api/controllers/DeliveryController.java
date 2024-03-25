package com.fujitsu.api.controllers;

import com.fujitsu.api.services.DeliveryFeeCalculatorImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Delivery", description = "API operations with delivery")
@RestController
@RequestMapping("/api/v1/delivery")
@AllArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryFeeCalculatorImpl deliveryFeeCalculator;

    @Operation(summary = "Calculate delivery fee depending on the city and type of vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee calculated successfully",
                    content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "403",
                    description = "Prohibited type of vehicle due to weather conditions",
                    content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "404",
                    description = "There is no weather information in the specified city",
                    content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "422",
                    description = "Invalid vehicle type",
                    content = @Content(mediaType = "*/*"))
    })
    @GetMapping("/fee")
    public ResponseEntity<Double> getDeliveryFee(@RequestParam(name = "city") String city,
                                                 @RequestParam(name = "vehicleType") String vehicleType) {
        log.debug("REST request to get Delivery Fee {} - {}", city, vehicleType);
        Double fee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);
        return ResponseEntity.ok().body(fee);
    }

}
