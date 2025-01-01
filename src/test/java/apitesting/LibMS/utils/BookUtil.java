package apitesting.LibMS.utils;

import apitesting.LibMS.models.Book;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.rest.SerenityRest.given;

public class BookUtil {
    Book newBook;

    public Book postBook(Book book){
         this.newBook = given()
                .baseUri(APIConfig.BASE_URI)
                .basePath("/api/books")
                .body(book, ObjectMapperType.GSON)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON).post().getBody().as(Book.class, ObjectMapperType.GSON);
        return newBook;
    }
    @Step
    public void getBook(Integer id){
        given()
                .baseUri(APIConfig.BASE_URI)
                .basePath("/api/books")
                .when()
                .get("/" + id)
                .then()
                .statusCode(200);
    }
    @Step
    public void deleteBook(Integer id){
        System.out.println("ID:::::::::::::::::::"+ id);
        AuthenticationUtil.loginAsUser();
        given()
                .baseUri(APIConfig.BASE_URI)
                .basePath("/api/books")
                .delete("/"+id);
    }
}
