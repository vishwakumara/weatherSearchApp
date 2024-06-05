package com.example.myapplication;

public class Weather {
    private float temp;
    private float tempMin;
    private float tempMax;
    private int humidity;
    private int pressure;
    private float windSpeed;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherDescription() {
        return "Temperature: " + temp + "°C\n" +
                "Min Temp: " + tempMin + "°C\n" +
                "Max Temp: " + tempMax + "°C\n" +
                "Humidity: " + humidity + "%\n" +
                "Pressure: " + pressure + " hPa\n" +
                "Wind Speed: " + windSpeed + " m/s";
    }
}