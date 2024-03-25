package com.fujitsu.api.controllers;

import com.fujitsu.api.services.DeliveryFeeCalculatorImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
@AllArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryFeeCalculatorImpl deliveryFeeCalculator;

    @GetMapping("/fee")
    public ResponseEntity<Double> getDeliveryFee(@RequestParam(name = "city") String city,
                                                 @RequestParam(name = "vehicleType") String vehicleType) {
        log.debug("REST request to get Delivery Fee {} - {}", city, vehicleType);
        Double fee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);
        return ResponseEntity.ok().body(fee);
    }

}
