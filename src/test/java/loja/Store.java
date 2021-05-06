package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordemDaExecucao() throws IOException {
      venderPet();
      consultarVendaPet();
      removerUser();

    }

    @Test
    public void venderPet() throws IOException {

        String jsonBody = lerJson("data/order.json");

        given()
                .log().all()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .post("https://petstore.swagger.io/v2/store/order")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(6))
                .body("petId", is(10))
                .body("status", is("placed"))
                .body("complete", is(true))
        ;
    }

    @Test
    public void consultarVendaPet() {

        String id = "6";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/store/order/" + id)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(6))
                .body("petId", is(10))
                .body("status", is("placed"))
                .body("complete", is(true))
        ;
    }

    @Test
    public void removerUser(){

        String id = "6";

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("https://petstore.swagger.io/v2/store/order/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(id))
        ;
    }
}
