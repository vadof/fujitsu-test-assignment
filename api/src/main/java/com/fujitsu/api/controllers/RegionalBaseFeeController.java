package com.fujitsu.api.controllers;

import com.fujitsu.api.dtos.RegionalBaseFeeDto;
import com.fujitsu.api.services.RegionalBaseFeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rbf")
@AllArgsConstructor
@Slf4j
public class RegionalBaseFeeController {

    private final RegionalBaseFeeService rbfService;

    @PatchMapping
    public ResponseEntity<RegionalBaseFeeDto> changeRbf(@RequestBody RegionalBaseFeeDto rbfDto) {
        log.debug("REST request to change RBF {}", rbfDto);
        RegionalBaseFeeDto result = rbfService.updateRBF(rbfDto);
        return ResponseEntity.ok().body(result);
    }

}
