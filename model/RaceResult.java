package model;

public class RaceResult {
    public final double timeSeconds;
    public final boolean won;
    public final int laneDistance;
    public final int finalDistance;
    public final double confidenceBefore;
    public final String trackShape;
    public final WeatherCondition weather;

    public RaceResult(
            double timeSeconds,
            boolean won,
            int laneDistance,
            int finalDistance,
            double confidenceBefore,
            String trackShape,
            WeatherCondition weather
    ) {
        this.timeSeconds = timeSeconds;
        this.won = won;
        this.laneDistance = laneDistance;
        this.finalDistance = finalDistance;
        this.confidenceBefore = confidenceBefore;
        this.trackShape = trackShape;
        this.weather = weather;
    }
}
