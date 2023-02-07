package main.java.chapter_02;

public class Application {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        ConditionDisplay conditionDisplay = new ConditionDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);

        weatherData.setMeasurements(12.0f,55f,20f);
        weatherData.setMeasurements(-1.0f,55f,17f);
    }
}
