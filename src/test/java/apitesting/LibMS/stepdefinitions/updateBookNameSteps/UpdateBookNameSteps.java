package apitesting.LibMS.stepdefinitions.updateBookNameSteps;

import apitesting.LibMS.utils.APIConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateBookNameSteps {
    private static final Logger logger = LoggerFactory.getLogger(UpdateBookNameSteps.class);
    private Response response;

    @Given("the database contains a book with ID {int}")
    public void the_database_contains_a_book_with_ID(int id) {
        response = given()
                .header("Content-Type", "application/json")
                .get(APIConfig.BASE_URI + "/api/books/" + id);

        if (response.statusCode() == 404) {
            throw new RuntimeException("Precondition failed: Book with ID " + id + " does not exist in the database.");
        }

        logger.info("Verified book with ID {} exists in the database.", id);
    }

    @When("I send a PUT request  to {string} with:")
    public void i_send_a_PUT_request_to_with(String endpoint, String body) {
        logger.info("Sending PUT request to endpoint: {} with body: {}", endpoint, body);
        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .put(APIConfig.BASE_URI + endpoint);
        logger.info("Received response: {}", response.getBody().asString());
    }

    @Then("I should  receive a {int} response code")
    public void i_should_receive_a_response_code(int expectedStatusCode) {
        logger.info("Validating response status code...");
        logger.info("Expected Status Code: {}, Actual Status Code: {}", expectedStatusCode, response.statusCode());
        assertEquals(expectedStatusCode, response.statusCode(), "Unexpected status code!");
    }
}