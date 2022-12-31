package main.java.chapter2;

public class ConditionDisplay implements Observer, DisplayElement{
    private float humidity;
    private float temperature;

    private WeatherData weatherData;

    public ConditionDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("now temp: " + temperature + ", 습도: " + humidity);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }
}
