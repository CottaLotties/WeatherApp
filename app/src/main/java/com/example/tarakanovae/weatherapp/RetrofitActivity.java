package com.example.tarakanovae.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import com.example.tarakanovae.weatherapp.retrofitInterface.WeatherApi;
import com.example.tarakanovae.weatherapp.retrofitModel.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Activity that gets and shows the forecast
public class RetrofitActivity extends AppCompatActivity {

    TextView city, date, temperature, conditions;
    ImageView picture;
    Context con;
    EditText loc;
    int ACCESS_LOCATION_ATTEMPTS=0;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_activity);
        con = this;

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        city = (TextView) findViewById(R.id.city);
        date = (TextView) findViewById(R.id.date);
        temperature = (TextView) findViewById(R.id.temperature);
        conditions = (TextView) findViewById(R.id.conditions);
        picture = (ImageView) findViewById(R.id.weatherIconImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        change();
    }

    private void change() {
        char unit = 'c';
        loc = (EditText) findViewById(R.id.locationEditText);

        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", ChooseLocationActivity.LOCATION_OF_CHOICE);
        WeatherApi.Factory.getInstance().getWeather(YQL, "json").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                try{
                temperature.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp());
                city.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());
                date.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
                conditions.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());

                int resourceId = getResources().getIdentifier("drawable/icon_" + response.body().getQuery().getResults().getChannel().getItem().getCondition().getCode(), null, getPackageName());
                @SuppressWarnings("deprecation")
                Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
                picture.setImageDrawable(weatherIconDrawable);

                //вывод прогноза на десять суток
                ArrayList<String> from = new ArrayList<String>();
                from.add("Date");
                from.add("Day");
                from.add(" ");
                from.add(" ");
                from.add("Weather");

                for (int i = 0; i < response.body().getQuery().getResults().getChannel().getItem().getForecast().size(); i++) {
                    from.add(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getDate());
                    from.add(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getDay());
                    from.add(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getHigh() + "");
                    from.add(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getLow() + "");
                    from.add(response.body().getQuery().getResults().getChannel().getItem().getForecast().get(i).getText());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(con, R.layout.retrofit_forecast_item, R.id.cell, from);
                GridView grid = (GridView) findViewById(R.id.retrofit_grid);
                grid.setAdapter(adapter);
                grid.setNumColumns(5);

                 ACCESS_LOCATION_ATTEMPTS=0;
                    progress.dismiss();

                } catch (Exception e){
                    //we try to get the result seven times. If it fails, we show the error
                    if (ACCESS_LOCATION_ATTEMPTS<7){
                       change();
                        ACCESS_LOCATION_ATTEMPTS++;
                    }
                    else{
                    Toast.makeText(con, "no weather data", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(con, "no weather data", Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });

    }
}
