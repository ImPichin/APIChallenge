package RestfullBooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ApiConfig.ApiPaths.apiPaths.UPDATE_BOOKING;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBooking {

    CreateToken createToken = new CreateToken();

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    @Test
    public void successUpdateBookingTest(){

        String token = createToken.createToken();

        RestAssured
                .given()
                .when().headers("Accept", "application/json",
                        "Cookie", "token="+token)
                .body("{\n" +
                        "    \"firstname\" : \"Giru\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .put(UPDATE_BOOKING)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstname", equalTo("Giru"));
    }

    @Test
    public void tryToUpdateBookingWithoutTokenTest(){

        RestAssured
                .given()
                .when().headers("Accept", "application/json",
                        "Cookie", "token=")
                .body("{\n" +
                        "    \"firstname\" : \"Edit Name\",\n" +
                        "    \"lastname\" : \"Edit Lastname\",\n" +
                        "    \"totalprice\" : 222,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast edited\"\n" +
                        "}")
                .put(UPDATE_BOOKING)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
