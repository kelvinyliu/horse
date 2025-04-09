package model;

public class Race {
    private int raceLength;
    private int laneCount;
    private String trackShape;
    private WeatherCondition weatherCondition;

    public Race(int distance) {
        this.raceLength = distance;
    }

    public void configureRace(int raceLength, int laneCount, String trackShape, WeatherCondition weatherCondition) {
        this.raceLength = raceLength;
        this.laneCount = laneCount;
        this.trackShape = trackShape;
        this.weatherCondition = weatherCondition;
    }

    public int getRaceLength() {
        return raceLength;
    }

    public int getLaneCount() {
        return laneCount;
    }

    public String getTrackShape() {
        return trackShape;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }
}