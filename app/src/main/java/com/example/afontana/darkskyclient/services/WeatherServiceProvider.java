package com.example.afontana.darkskyclient.services;

import android.util.Log;

import com.example.afontana.darkskyclient.Currently;
import com.example.afontana.darkskyclient.Weather;
import com.example.afontana.darkskyclient.events.ErrorEvent;
import com.example.afontana.darkskyclient.events.WeatherEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {
    private static final String BASE_URL = "https://api.darksky.net/forecast/fb4f048dc6597c8741c851b7233e6443/";
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private Retrofit retrofit;

    private Retrofit getRetrofit() {
        if (this.retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getWeather(double lat, double lng) {
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<Weather> weatherData = weatherService.getWeather(lat, lng);
        weatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<com.example.afontana.darkskyclient.Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                if (weather != null) {
                    Currently currently = weather.getCurrently();
                    Log.e(TAG, "Temperature = " + currently.getTemperature());
                    EventBus.getDefault().post(new WeatherEvent(weather));
                } else {
                    Log.e(TAG, "No Response: check secret key");
                    EventBus.getDefault().post(new ErrorEvent("No weather data available"));
                }
            }

            @Override
            public void onFailure(Call<com.example.afontana.darkskyclient.Weather> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to get weather data");
                EventBus.getDefault().post(new ErrorEvent("Unable to connect weather server"));
            }
        });
    }
}

