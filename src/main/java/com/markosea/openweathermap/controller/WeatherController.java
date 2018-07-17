package com.markosea.openweathermap.controller;

import com.markosea.openweathermap.model.WeatherLog;
import com.markosea.openweathermap.service.WeatherService;
import com.markosea.openweathermap.wrapper.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public List<WeatherInfo> getWeatherInfo() {
        return weatherService.getWeatherInfo();
    }

    @GetMapping("/logs")
    public List<WeatherLog> getWeatherLogs () {
        return weatherService.getWeatherLogs();
    }

}
