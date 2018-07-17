package com.markosea.openweathermap.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class WeatherLog {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String responseId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String actualWeather;

    @Column(nullable = false)
    private String temperature;

    @Column(nullable = false)
    private Timestamp dtimeInserted;


    public WeatherLog() {
    }

    public WeatherLog(String location, String actualWeather, String temperature, Timestamp dtimeInserted) {
        this.location = location;
        this.actualWeather = actualWeather;
        this.temperature = temperature;
        this.dtimeInserted = dtimeInserted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActualWeather() {
        return actualWeather;
    }

    public void setActualWeather(String actualWeather) {
        this.actualWeather = actualWeather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Timestamp getDtimeInserted() {
        return dtimeInserted;
    }

    public void setDtimeInserted(Timestamp dtimeInserted) {
        this.dtimeInserted = dtimeInserted;
    }

    @PrePersist
    public void prePersist() {
        this.responseId = UUID.randomUUID().toString();
    }


}
