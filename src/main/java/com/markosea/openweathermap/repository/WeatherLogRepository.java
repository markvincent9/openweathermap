package com.markosea.openweathermap.repository;

import com.markosea.openweathermap.model.WeatherLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherLogRepository extends CrudRepository<WeatherLog, Long> {
    WeatherLog findFirstByLocationOrderByDtimeInsertedDesc(String location);

    List<WeatherLog> findFirst5ByOrderByDtimeInsertedDesc();
}
