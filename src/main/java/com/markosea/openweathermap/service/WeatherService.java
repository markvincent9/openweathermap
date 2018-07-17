package com.markosea.openweathermap.service;

import com.markosea.openweathermap.model.WeatherLog;
import com.markosea.openweathermap.repository.WeatherLogRepository;
import com.markosea.openweathermap.wrapper.City;
import com.markosea.openweathermap.wrapper.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    private List<City> cityList = Arrays.asList(new City("london", "uk"),
            new City("prague", "cz"),
            new City("San Francisco", "us"));

    private final RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String API_KEY;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<WeatherInfo> getWeatherInfo () {
        List<WeatherInfo> weatherInfos =  cityList.stream().map(c -> getCityWeather(c)).collect(Collectors.toList());
        monitorService.monitor(weatherInfos);
        return weatherInfos;
    }

    public List<WeatherLog> getWeatherLogs(){
        return weatherLogRepository.findFirst5ByOrderByDtimeInsertedDesc();
    }

    private WeatherInfo getCityWeather(City city) {
        URI uri = new UriTemplate(WEATHER_URL).expand(city.getName(), city.getCountry(), API_KEY);
        return this.restTemplate.getForObject(uri, WeatherInfo.class);
    }

}
