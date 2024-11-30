import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.WeatherDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws RuntimeException {
        APIHandler apiHandler = new APIHandler();
        Map<String, Object> params = new HashMap<>();
        params.put("lat", 55.75);
        params.put("lon", 37.62);
        String response;
        try {
            response = apiHandler.call("/forecast", params);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении ответа API", e);
        }
        System.out.println("Ответ API: " + response + "\n");

        final Gson gson = new Gson();
        WeatherDTO dto = null;
        try {
            dto = gson.fromJson(response, WeatherDTO.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Ошибка при обработке JSON", e);
        }

        System.out.println("Текущая температура - " + dto.fact().temp() + " C");

        int limit = 5;
        int totalDays = Math.min(limit, dto.forecasts().length);
        System.out.println(
                "Средняя температура за " + totalDays + " дней - " +
                        averageTemperatureFromDTO(dto, 5) + " C"
        );
    }

    private static double averageTemperatureFromDTO(WeatherDTO dto, int limit) {
        double sum = 0;
        int n = Math.min(limit, dto.forecasts().length);
        for (int i = 0; i < n; i++) {
            sum += dto.forecasts()[i].parts().temp_avg();
        }
        return sum / n;
    }
}