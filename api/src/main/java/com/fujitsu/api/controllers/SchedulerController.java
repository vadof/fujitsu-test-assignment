package com.fujitsu.api.controllers;

import com.fujitsu.api.schedulers.WeatherScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Schedule", description = "API operations to configure schedules")
@RestController
@RequestMapping("/api/v1/schedules")
@AllArgsConstructor
@Slf4j
public class SchedulerController {

    private WeatherScheduler weatherScheduler;

    @Operation(summary = "Set a cron expression to update weather data schedules" +
            " (will be applied after the current one is activated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Weather update schedules has been updated."),
            @ApiResponse(responseCode = "400",
                    description = "Invalid cron expression")
    })
    @PostMapping(value = "/weather", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void updateWeatherDataSchedule(@RequestBody String cronExpression) {
        log.debug("REST request to update cron expression {}", cronExpression);
        weatherScheduler.setNewSchedule(cronExpression);
    }
}
