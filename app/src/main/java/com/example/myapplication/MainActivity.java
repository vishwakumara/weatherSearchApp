package com.example.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tvLatLng, tvTime, tvAddress, tvWeatherInfo;
    private com.android.volley.RequestQueue requestQueue;
    private static final double LAT = 6.800089842469832;
    private static final double LNG =  79.99287481556635;
    private static final String OPEN_WEATHER_MAPS_API_KEY = "5db40e6a35a5fb7ae50735ab08f257a7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatLng = findViewById(R.id.tvLatLng);
        tvTime = findViewById(R.id.tvTime);
        tvAddress = findViewById(R.id.tvAddress);
        tvWeatherInfo = findViewById(R.id.tvWeatherInfo);

        requestQueue = Volley.newRequestQueue(this);

        tvLatLng.setText("Latitude " + LAT + " : " + "Longitude " + LNG);
        getWeatherData();
        getAddress();
        updateTime();
    }

    private void getWeatherData() {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + LAT + "&lon=" + LNG + "&units=metric&appid=" + OPEN_WEATHER_MAPS_API_KEY;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Weather weather = parseWeatherData(response);
                            tvWeatherInfo.setText(weather.getWeatherDescription());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Failed to parse weather data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to get weather data", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private Weather parseWeatherData(JSONObject response) throws JSONException {
        Weather weather = new Weather();
        JSONObject main = response.getJSONObject("main");
        weather.setTemp((float) main.getDouble("temp"));
        weather.setTempMin((float) main.getDouble("temp_min"));
        weather.setTempMax((float) main.getDouble("temp_max"));
        weather.setHumidity(main.getInt("humidity"));
        weather.setPressure(main.getInt("pressure"));
        JSONObject wind = response.getJSONObject("wind");
        weather.setWindSpeed((float) wind.getDouble("speed"));
        return weather;
    }

    private void getAddress() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LAT, LNG, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
            }
            tvAddress.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            tvAddress.setText("No specific address available");
        }
    }

    private void updateTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String currentDateTime = dateFormat.format(new Date());
                            tvTime.setText(currentDateTime);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class RequestQueue {
        public void add(JsonObjectRequest request) {
        }
    }
}
