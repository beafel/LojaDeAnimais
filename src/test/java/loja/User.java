package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class User {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordernarExecucao() throws IOException {
        incluirUser();
        consultarUser();
        alterarUsuario();
        consultarUser();
        removerUser();
    }

    //@Test
    public void incluirUser() throws IOException {

        String jsonBody = lerJson("data/user.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/user")
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5"))
        ;

        System.out.print("Executou o servico");
    }

    //@Test
    public void consultarUser() {

        String username = "alicer";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(5))
                .body("firstName", is("Alice"))
                .body("lastName", is("Root"))
                .body("email", is("alicert@petstore.com"))
        ;
        System.out.println("Executou a consulta");
    }

    //@Test
    public void alterarUsuario() throws IOException {
        String jsonBody = lerJson("data/userput.json");
        String username = "alicer";

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("https://petstore.swagger.io/v2/user/" + username)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("5"))
        ;
    }

    //@Test
    public void removerUser(){

        String username = "alicer";

        given()
                .log().all()
                .contentType("application/json")
        .when()
                .delete("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is(username))
        ;
    }
}
