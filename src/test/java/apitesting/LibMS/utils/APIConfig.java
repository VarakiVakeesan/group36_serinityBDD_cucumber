package apitesting.LibMS.utils;

public class APIConfig {
    public static final String BASE_URI = "http://localhost:7081";
    public static final String API_URI = BASE_URI + "/api";
    public static final String POST_URI = API_URI + "/books";
    public static final String GET_URI = API_URI + "/books";
    public static final String GET_BY_ID_URI = API_URI + "/books/{id}";
    public static final String PUT_URI = API_URI + "/books/";
}
