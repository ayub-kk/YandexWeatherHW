import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Main {
    public static String API = "c0f57a1c-e34d-4ca4-977b-7d5ecb523a94";
    public static String URL = "https://api.weather.yandex.ru/v2/forecast";
    public static void main(String[] args) throws IOException {
        double lat = 55.75;
        double lon = 37.62;

        String urlString = URL + "?lat=" + lat + "&lon=" + lon;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Yandex-Weather-Key", API);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        System.out.println("Полные данные: " + jsonResponse.toString());

        int currentTemp = jsonResponse.getJSONObject("fact").getInt("temp");
        System.out.println("Текущая температура: " + currentTemp);

        double avgTemp = 0;

        for (int i = 0; i < 7; i++) {
            avgTemp += jsonResponse.getJSONArray("forecasts").getJSONObject(i)
                    .getJSONObject("parts").getJSONObject("day").getDouble("temp_avg");
        }

        avgTemp /= 7;
        System.out.println("Средняя температура за период: " + avgTemp);
    }
}
