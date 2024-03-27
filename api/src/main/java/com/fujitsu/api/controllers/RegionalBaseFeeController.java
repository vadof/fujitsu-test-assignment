package com.fujitsu.api.controllers;

import com.fujitsu.api.dtos.RegionalBaseFeeDto;
import com.fujitsu.api.services.RegionalBaseFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rbf")
@Tag(name = "RBF", description = "API operations with RBF")
@AllArgsConstructor
@Validated
@Slf4j
public class RegionalBaseFeeController {

    private final RegionalBaseFeeService rbfService;

    @Operation(summary = "Update RBF price ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "RBF price updated successfully",
                    content = @Content(schema = @Schema(implementation = RegionalBaseFeeDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "City not found", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "422",
                    description = "Invalid vehicle type", content = @Content(mediaType = "*/*"))
    })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeDto> changeRbf(@Valid @RequestBody RegionalBaseFeeDto rbfDto) {
        log.debug("REST request to change RBF {}", rbfDto);
        RegionalBaseFeeDto result = rbfService.updateRBF(rbfDto);
        return ResponseEntity.ok().body(result);
    }

}
