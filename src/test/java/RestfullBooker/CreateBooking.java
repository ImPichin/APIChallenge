package RestfullBooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ApiConfig.ApiPaths.apiPaths.CREATE_BOOKING;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBooking {

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    @Test
    public void createSuccessBookingTest(){

        RestAssured
                .given()
                .body("{\n" +
                        "    \"firstname\" : \"Juanito\",\n" +
                        "    \"lastname\" : \"Testing\",\n" +
                        "    \"totalprice\" : 696,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2022-01-01\",\n" +
                        "        \"checkout\" : \"2023-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .post(CREATE_BOOKING)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Juanito"));
    }

    @Test
    public void tryToCreateIncompleteBookingTest(){

        RestAssured
                .given()
                .body("{\n" +
                        "    \"lastname\" : \"WithoutFirstName\",\n" +
                        "    \"totalprice\" : 123,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2022-01-01\",\n" +
                        "        \"checkout\" : \"2023-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Lunch\"\n" +
                        "}")
                .post(CREATE_BOOKING)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void tryToCreateBookingTest(){

        RestAssured
                .given()
                .body("{\n" +
                        "    \"firstname\" : \"Juan David\",\n" +
                        "    \"lastname\" : \"Testing App\",\n" +
                        "    \"totalprice\" : 1231.2,\n" +
                        "    \"depositpaid\" : false,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"InitialDate\",\n" +
                        "        \"checkout\" : \"EndDate\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"---\"\n" +
                        "}")
                .post(CREATE_BOOKING)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Juan David"));
    }
}
