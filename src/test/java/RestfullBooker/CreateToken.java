package RestfullBooker;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

import static ApiConfig.ApiPaths.apiPaths.CREATE_TOKEN;
import static org.hamcrest.Matchers.notNullValue;

public class CreateToken {

    public String createToken(){

        String token = RestAssured
                .given()
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .post(CREATE_TOKEN)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .extract().jsonPath().getString("token");

        return token;
    }
}
