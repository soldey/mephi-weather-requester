import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APIHandler {
    private static final String YANDEX_WEATHER_API_KEY = "94580c6a-e84f-4c54-925b-e3033cb31c52";
    private static final String API_PREFIX = "https://api.weather.yandex.ru/v2";

    public String call(final String endpoint, final Map<String, Object> queryParams) throws IOException {
        String url = API_PREFIX + endpoint;
        if (!queryParams.isEmpty()) {
            List<String> params = new ArrayList<>();
            queryParams.forEach((key, value) -> params.add(key + "=" + value.toString()));
            url += "?" + String.join("&", params);
        }
        final URL urlObj = new URL(url);
        final HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Yandex-API-Key", YANDEX_WEATHER_API_KEY);

        final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        final StringBuilder response = new StringBuilder();

        String input;
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();
        conn.disconnect();
        return response.toString();
    }
}
