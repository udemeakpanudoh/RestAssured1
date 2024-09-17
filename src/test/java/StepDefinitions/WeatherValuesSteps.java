package StepDefinitions;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class WeatherValuesSteps {

    @Given("I send a request to search tomorrow's weather")
    public void i_send_a_request_to_search_tomorrows_weather() {
        // Set the base URI
        RestAssured.baseURI = "https://api.tomorrow.io/v4/weather/forecast";

        // Base URI and endpoint
        //String baseUri = "https://api.tomorrow.io/v4/weather/forecast";
        String apiKey = "gGyTeS1ar5kTLZmtdpmQ07fL65pbbrvF";
        String location = "42.3478,-71.0466";

        // Make the GET request
        Response response = RestAssured.given()
                .queryParam("location", location)
                .queryParam("apikey", apiKey)
                .when()
                .get()
                .then()
                .statusCode(200) // Check if the response is successful
                .extract()
                .response();

        //String displayResults = response.asPrettyString();
        //System.out.println("Results of response is: " + displayResults);

        // Extract the response body
        JsonPath jsonPath = response.jsonPath();

        // Get the list of timelines and filter by the specific time
        List<Map<String, Object>> timelines = jsonPath.getList("timelines.hourly");

        System.out.println("The Map List returns the following:  " + timelines);


        // Filter for the required time
        timelines.stream()
                .filter(data -> "2024-04-10T22:00:00Z".equals(data.get("time")))
                .forEach(data -> System.out.println("Weather data for 2024-04-10T22:00:00Z: " + data));
    }

}