package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Pet {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordemDaExecucao() throws IOException {
        incluirPet();
        consultarPet();
        alterarPet();
        consultarPet();
        excluirPet();

    }


    // Create / Incluir / POST
    //@Test
    public void incluirPet() throws IOException {
        //String tokenLocal = loginUser2();
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/pet.json");

        given()                                         // Dado que
                .contentType("application/json")        // Tipo de conteúdo da requisição
                                                        // "text/xml" para web services comuns
                                                        // "application/json" para APIs REST
                .log().all()                            // Gerar um log completo da requisição
                .body(jsonBody)                         // Conteúdo do corpo da requisição
                .when()                                 // Quando (eh a operacao em si POST, PUT, DELETE)
                .post("https://petstore.swagger.io/v2/pet") // Operação e endpoint
                .then()                                 // Entao (eh a validacao)
                .log().all()                            // Gerar um log completo da resposta
                .statusCode(200)                        // Validou o código de status da requisição como 200
                // .body("code", is(200))               // Valida o code como 200
                .body("id", is(10))         // Validou a tag id com o conteúdo esperado
                .body("name", is("Manolo"))   // Validou a tag nome como Garfield
                .body("tags.name", contains("adoption")) // Validou a tag Name filha da tag Tags
        ;
        System.out.print("Executou o servico");
    }

    // Reach or Research / Get
    //@Test
    public void consultarPet(){
        String petId = "10";

        given()                                     // Pre-requisito ou pre-condicao
                .contentType("application/json")            // Tipo de conteúdo da requisição
                .log().all()                                // Mostrar tudo que foi enviado
        .when()                                     // Operacao em si
                .get("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
        .then()                                     // Validacoes, o teste em si
                .log().all()                                // Mostrar tudo que foi recebido
                .statusCode(200)                            // Validou que a operação foi realizada
                .body("name", is("Manolo"))     // Validou o nome do pet
                .body("category.name", is("dog")) // Validou a espécie
        ;
    }

    // Update / Put
    //@Test
    public void alterarPet() throws IOException {
        // Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("data/petput.json");

        given()                                     // Dado que
                .contentType("application/json")    // Tipo de conteúdo da requisição
                                                    // "text/xml" para web services comuns
                                                    // "application/json" para APIs REST
                .log().all()                        // Gerar um log completo da requisição
                .body(jsonBody)                     // Conteúdo do corpo da requisição
                .when()                             // Quando
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Manolo"))
                .body("status", is("adopted"))
        ;

    }

    // Delete / Delete
    //@Test
    public void excluirPet(){
        String petId = "10";

        given()                                         // Dado que
                .contentType("application/json")        // Tipo de conteúdo da requisição
                .log().all()                            // Mostrar tudo que foi enviado
                .when()                                 // Quando
                .delete("https://petstore.swagger.io/v2/pet/" + petId) // Consulta pelo petId
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message",is(petId))
        ;
    }
}