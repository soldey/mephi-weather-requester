package dto;

public record WeatherDTO(Fact fact, Forecast[] forecasts) {

    public record Fact(double temp) {}

    public record Forecast(Parts parts) {
        public record Parts(double temp_avg) {}
    }
}
