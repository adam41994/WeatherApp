package com.example.afontana.darkskyclient.events;

import com.example.afontana.darkskyclient.Weather;

public class WeatherEvent {
    private final Weather weather;

    public WeatherEvent(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }
}
