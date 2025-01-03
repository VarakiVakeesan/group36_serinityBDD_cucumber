package apitesting.LibMS.utils;

import apitesting.LibMS.stepdefinitions.getBookByIdSteps.GetBookByIdSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProvideNonExistBookID {
    public static List<Integer> existingBookIds;
    public static int nonExistingBookId;
    public static final String GET_URI = "http://localhost:7081/api/books";
    public static Response response;

    public void provideNonExistBookID() {
        GetBookByIdSteps.existingBookIds = RestAssured
                .given()
                .get(GET_URI)
                .jsonPath()
                .getList("id", Integer.class);
        Random random = new Random();
        do {
            nonExistingBookId = random.nextInt(1000);
        } while (existingBookIds.contains(nonExistingBookId));

    }

}
