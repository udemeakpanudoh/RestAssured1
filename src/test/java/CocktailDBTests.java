import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CocktailDBTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://www.thecocktaildb.com/api/json/v1/1";
    }

    @Test
    public void testSearchIngredientByName() {
        Response response = RestAssured
                .given()
                .queryParam("i", "vodka")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String jsonString = response.asString();
        String ingredientId = response.jsonPath().getString("ingredients[0].idIngredient");
        String ingredient = response.jsonPath().getString("ingredients[0].strIngredient");
        String description = response.jsonPath().getString("ingredients[0].strDescription");
        String type = response.jsonPath().getString("ingredients[0].strType");
        String alcohol = response.jsonPath().getString("ingredients[0].strAlcohol");
        String abv = response.jsonPath().getString("ingredients[0].strABV");

        Assert.assertNotNull(ingredientId);
        Assert.assertEquals(ingredient, "Vodka");
        Assert.assertNotNull(description);
        Assert.assertNotNull(type);
        Assert.assertEquals(alcohol, "Yes");
        Assert.assertNotNull(abv);
    }

    @Test
    public void testSearchCocktailByName() {
        Response response = RestAssured
                .given()
                .queryParam("s", "margarita")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().get("drinks"));
        String drinkName = response.jsonPath().getString("drinks[0].strDrink");
        Assert.assertEquals(drinkName.toLowerCase(), "margarita");
    }

    @Test
    public void testSearchNonExistentCocktail() {
        Response response = RestAssured
                .given()
                .queryParam("s", "nonexistentcocktail")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNull(response.jsonPath().get("drinks"));
    }

    @Test
    public void testSearchCocktailByNameCaseInsensitive() {
        Response response = RestAssured
                .given()
                .queryParam("s", "MARGARITA")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().get("drinks"));
        String drinkName = response.jsonPath().getString("drinks[0].strDrink");
        Assert.assertEquals(drinkName.toLowerCase(), "margarita");
    }

    @Test
    public void testCocktailSchema() {
        Response response = RestAssured
                .given()
                .queryParam("s", "margarita")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().get("drinks[0].strDrink"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strTags"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strCategory"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strAlcoholic"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strGlass"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strInstructions"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strIngredient1"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strMeasure1"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].strCreativeCommonsConfirmed"));
        Assert.assertNotNull(response.jsonPath().get("drinks[0].dateModified"));
    }

    // Test Case 1: Verify search result for a common ingredient with multiple entries
    @Test
    public void testSearchCommonIngredient() {
        Response response = RestAssured
                .given()
                .queryParam("i", "lime")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().get("ingredients"));
        Assert.assertTrue(response.jsonPath().getList("ingredients").size() > 0);

    }

    // Test Case 2: Verify search by partial cocktail name
    @Test
    public void testSearchByPartialCocktailName() {
        Response response = RestAssured
                .given()
                .queryParam("s", "mar")
                .when()
                .get("/search.php")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().get("drinks"));
        Assert.assertTrue(response.jsonPath().getList("drinks").size() > 1);
    }
}