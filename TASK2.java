import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;

public class Main {

    // Replace with your actual WeatherAPI key
    private static final String API_KEY = "8d16a2175ffa41bd9a5154444250705";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public static void main(String[] args) {
        String city = "London"; // Change or take from user input

        try {
            String url = String.format("%s?key=%s&q=%s", BASE_URL, API_KEY, city);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                parseAndDisplayWeather(response.body());
            } else {
                System.out.println("Failed to fetch weather data. HTTP status code: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void parseAndDisplayWeather(String json) {
        JSONObject obj = new JSONObject(json);

        String cityName = obj.getJSONObject("location").getString("name");
        JSONObject current = obj.getJSONObject("current");
        double temp = current.getDouble("temp_c");
        int humidity = current.getInt("humidity");
        String condition = current.getJSONObject("condition").getString("text");

        System.out.println("Weather Information for " + cityName);
        System.out.println("Temperature: " + temp + "°C");
        System.out.println("Humidity   : " + humidity + "%");
        System.out.println("Condition  : " + condition);
    }
}