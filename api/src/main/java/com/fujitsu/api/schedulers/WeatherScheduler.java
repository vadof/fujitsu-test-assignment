package com.fujitsu.api.schedulers;

import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.services.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduler implements SchedulingConfigurer {

    private final WeatherService weatherService;

    @Value("${weather.cron.expression}")
    private String cronExpression;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(weatherService::updateWeatherData, triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            return cronTrigger.nextExecution(triggerContext);
        });
    }

    public void setNewSchedule(String cronExpression) {
        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new AppException("Invalid cron expression " + cronExpression, HttpStatus.BAD_REQUEST);
        }
        this.cronExpression = cronExpression;
    }

}
