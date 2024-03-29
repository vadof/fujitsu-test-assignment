package com.fujitsu.api.controllers;

import com.fujitsu.api.services.delivery.DeliveryFeeCalculatorImpl;
import com.fujitsu.api.services.RegionalBaseFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "Delivery", description = "API operations with delivery")
@RestController
@RequestMapping("/api/v1/delivery")
@AllArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryFeeCalculatorImpl deliveryFeeCalculator;
    private final RegionalBaseFeeService regionalBaseFeeService;

    @Operation(summary = "Calculate delivery fee depending on the city and type of vehicle." +
            " If a date is specified, the fee will be calculated based on weather data that occurred during the specified period of time")
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
    @GetMapping(value = "/fee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getDeliveryFee(
            @RequestParam(name = "city") String city,
            @RequestParam(name = "vehicleType") String vehicleType,
            @RequestParam(name = "date", required = false) LocalDateTime date) {
        log.debug("REST request to get Delivery Fee {} - {}", city, vehicleType);
        Double fee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType, date);
        return ResponseEntity.ok().body(fee);
    }

    @Operation(summary = "Get a map with available vehicle types in each city " +
            "where the key is the city and the value is a list with vehicle types")
    @ApiResponse(responseCode = "200", description = "Return map")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<String>>> getAvailableVehicleMap() {
        log.debug("REST request to get available vehicle map");
        Map<String, List<String>> map = regionalBaseFeeService.getMapWithAvailableVehiclesInCities();
        return ResponseEntity.ok().body(map);
    }

}
