package apitesting.LibMS.stepdefinitions.getBookByIdSteps;


import apitesting.LibMS.utils.APIConfig;
import apitesting.LibMS.utils.ApiRequest;
import apitesting.LibMS.utils.ProvideNonExistBookID;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GetBookByIdSteps {
    public static List<Integer> existingBookIds;
    public static int nonExistingBookId;
    public static final String GET_URI = "http://localhost:7081/api/books";
    public static Response response;



    private static final Logger logger = LoggerFactory.getLogger(GetBookByIdSteps.class);

    @Given("the database does not contain a book with ID {int}")
    public void the_database_does_not_contain_a_book_with_ID(int id) {
        ApiRequest.get("/api/books/" + id);

        if (ApiRequest.response.statusCode() != 404) {
            ApiRequest.delete("/api/books/" + id);
        }
    }

    @When("I send a GET request with non existing Book Id")
    public void iSendAGETRequestWithNonExistingBookId() {
        ProvideNonExistBookID.nonExistingBookId = nonExistingBookId;
        String fullUrl = APIConfig.GET_URI + "/" +nonExistingBookId;
        ApiRequest.response=RestAssured.given().get(fullUrl);
    }

}