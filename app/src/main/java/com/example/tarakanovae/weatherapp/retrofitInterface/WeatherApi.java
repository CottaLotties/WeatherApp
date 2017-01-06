package com.example.tarakanovae.weatherapp.retrofitInterface;
import com.example.tarakanovae.weatherapp.retrofitModel.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

//interface for getting data
public interface WeatherApi{

    String BASE_URL = "https://query.yahooapis.com/";
    @GET("v1/public/yql")
    Call<Weather> getWeather(@Query("q") String query,
                             @Query("format") String format);

    class Factory {

        private static WeatherApi service;
        public static WeatherApi getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(WeatherApi.class);
                return service;
            }
            else {
                return service;
            }
        }
    }
}