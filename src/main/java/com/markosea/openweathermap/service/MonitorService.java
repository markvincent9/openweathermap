package com.markosea.openweathermap.service;

import com.markosea.openweathermap.model.WeatherLog;
import com.markosea.openweathermap.repository.WeatherLogRepository;
import com.markosea.openweathermap.wrapper.Weather;
import com.markosea.openweathermap.wrapper.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    public void monitor(List<WeatherInfo> weatherInfos) {
        weatherInfos.forEach(weatherInfo -> {
            // find last entry by location
            WeatherLog weatherLog = weatherLogRepository.findFirstByLocationOrderByDtimeInsertedDesc(weatherInfo.getName());
            if (weatherLog == null) {
                // create first weather log by location
                createWeatherLog(weatherInfo);
            } else {
                // detect changes when dt is change in openweatherapi, some of the values are updated
                if (weatherLog.getDtimeInserted().before(new Timestamp(weatherInfo.getDt()))) {
                    createWeatherLog(weatherInfo);
                }
            }
        });

    }

    private void createWeatherLog(WeatherInfo weatherInfo) {
        WeatherLog w = weatherLogRepository.save(new WeatherLog(weatherInfo.getName(),
                weatherInfo.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")),
                Float.toString(weatherInfo.getMain().getTemp()),
                new Timestamp(weatherInfo.getDt())));

        logger.info("Creating Weather Log: Location '{}', Temp '{}', Weather '{}', DT '{}'", w.getLocation(),
                w.getTemperature(), w.getActualWeather(), w.getDtimeInserted());
    }


}
