import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqResApiTests extends TestBase {

    @DisplayName("Создание пользователя")
    @Test
    public void createUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"leader\"}";
        given()
                .body(userData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body().
        when()
                .post("/users").
        then()
                .log().status()
                .log().body()
                .statusCode(201)

                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @DisplayName("Редактирование пользователя")
    @Test
    public void updateUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"zion resident\"}";
        given()
                .body(userData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body().
        when()
                .put("/users/2").
        then()
                .log().status()
                .log().body()
                .statusCode(200)

                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Частичное редактирование пользователя")
    @Test
    public void partUpdateUserTest() {
        String userData = "{\"name\":\"morpheus\",\"job\":\"zion resident\"}";
        given()
                .body(userData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body().
        when()
                .patch("/users/2").
        then()
                .log().status()
                .log().body()
                .statusCode(200)

                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Удаление пользователя")
    @Test
    public void deleteUserTest() {
        given()
                .log().method()
                .log().uri().
        when()
                .delete("/users/2").
        then()
                .log().status()
                .log().body()
                .statusCode(204);
    }


    @DisplayName("Список пользователей")
    @Test
    public void usersListTest() {
        given()
                .log().method()
                .log().uri().
        when()
                .get("/users?page=2").
        then()
                .log().status()
                .log().body()
                .statusCode(200)

                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", hasSize(6));
    }
}