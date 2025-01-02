package apitesting.LibMS.stepdefinitions.createBook;

import apitesting.LibMS.utils.ApiRequest;
import apitesting.LibMS.utils.AuthenticationUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewBookSteps {
    private static final Logger logger = LoggerFactory.getLogger(CreateNewBookSteps.class);
    Response response;
    public static List<Integer> createdBooksIDs = new ArrayList<>();

    @When("I send a POST request to {string} with:")
    public void i_send_a_POST_request_to_with(String endpoint, String body) {
        ApiRequest.post(endpoint, body);
        this.response = ApiRequest.response;

        if (ApiRequest.response.statusCode() == 201) {
            String responseBody = ApiRequest.response.getBody().asString();
            JsonPath jsonPath = new JsonPath(responseBody);
            int createdBookId = jsonPath.getInt("id"); // Assuming the response contains the book ID
            createdBooksIDs.add(createdBookId); // Add the created book ID to the list
            logger.info("Book created with ID: {}", createdBookId);
        } else {
            System.err.println("POST request failed. Status Code: " + ApiRequest.response.statusCode());
        }
    }

    @Then("I should receive a {int} response code")
    public void i_should_receive_a_response_code(int expectedStatusCode) {
        logger.info("Validating response status code...");
        logger.info("Expected Status Code: {}, Actual Status Code: {}", expectedStatusCode, ApiRequest.response.statusCode());
        assertEquals(expectedStatusCode, ApiRequest.response.statusCode(), "Unexpected status code!");
    }

    @Then("the response should contain the same book details:")
    public void the_response_should_contain_the_same_book_details(String expectedResponseBody) {
        logger.info("Validating response body...");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode expectedJson = objectMapper.readTree(expectedResponseBody.trim());
            JsonNode actualJson = objectMapper.readTree(ApiRequest.response.getBody().asString().trim());
            String prettyExpected = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedJson);
            String prettyActual = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualJson);
            logger.info("Expected Response Body:\n{}", prettyExpected);
            logger.info("Actual Response Body:\n{}", prettyActual);
            assertEquals(prettyExpected, prettyActual, "Response body does not match expected!");
        } catch (Exception e) {
            logger.error("Error parsing or comparing JSON", e);
            throw new RuntimeException("Error validating JSON response", e);
        }
    }

    @AfterAll
    public static void after_all() {
        AuthenticationUtil.loginAsUser();
        logger.info("Deleting all created books...");
        for (int bookId : createdBooksIDs) {
            String deleteEndpoint = "/api/books/" + bookId;
            ApiRequest.delete(deleteEndpoint);
            logger.info("Deleted book with ID: {}", bookId);
        }
        createdBooksIDs.clear();
    }
}