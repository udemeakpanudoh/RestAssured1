import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Countries {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restcountries.com/v3.1";
    }

    @Test
    public void validateSchemaOfResponse() {

        String schemaPath = "src/test/resources/schema.json";

        given()
                .when()
                .get("/all")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)))
                .log().all();
    }

    @Test
    public void countNumberOfCountries() {
        int countryCount = given()
                .when()
                .get("/all")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("$")
                .size();

        System.out.println("Number of countries listed: " + countryCount);
        assertTrue(countryCount > 0, "Country count should be greater than 0");
    }

    @Test
    public void locateSouthAfricaAndIdentifyLanguages() {
        Response response = given()
                .when()
                .get("/all")
                .then()
                .extract()
                .response();

        JSONArray countries = new JSONArray(response.asString());
        boolean southAfricaFound = false;
        JSONObject southAfrica = null;

        for (int i = 0; i < countries.length(); i++) {
            JSONObject country = countries.getJSONObject(i);
            if (((JSONObject) country).getJSONObject("name").getString("common").equalsIgnoreCase("South Africa")) {
                southAfricaFound = true;
                southAfrica = country;
                break;
            }
        }

        assertTrue(southAfricaFound, "South Africa is listed");

        if (southAfrica != null) {
            JSONObject languages = southAfrica.getJSONObject("languages");
            System.out.println("Official Languages of South Africa: " + languages.toString());

            boolean signLanguageFound = languages.toString().contains("Sign Language");
            assertTrue(signLanguageFound, "Sign Language is listed as one of the official languages");
        }
    }
}
