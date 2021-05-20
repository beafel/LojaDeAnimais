package loja;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class Login {

    public String tokenGeral; // variavel para receber o token

    // Login
    @Test
    public void loginUser(){

        String token =
                given()                                     // Dado que
                        .contentType("application/json")    // Tipo de conteúdo da requisição
                        .log().all()                        // Mostrar tudo que foi enviado
                .when()
                        .get("https://petstore.swagger.io/v2/user/login?username=alicer&password=asdfglkjh")
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("message", containsString("logged in user session:"))
                        .extract()
                        .path("message")
                ;
        tokenGeral = token.substring(23);                   // separa o token da frase
        System.out.println("O token valido: " + tokenGeral);
    }

    @Test
    // Poderia ser uma funcao com retorno do token
    public void chamaLogin(){           // a anotacao @Test precisa ter um metodo (void) que chama a funcao
        loginUser2();
    }

    public String loginUser2(){

        String token =
                given()                                      // Dado que
                        .contentType("application/json")     // Tipo de conteúdo da requisição
                        .log().all()                         // Mostrar tudo que foi enviado
                        .when()
                        .get("https://petstore.swagger.io/v2/user/login?username=charlie&password=brown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("message", containsString("logged in user session:"))
                        .extract()
                        .path("message")
                ;
        String tokenGeral2 = token.substring(23); // separa o token da frase
        System.out.println("O token valido: " + tokenGeral2);
        return tokenGeral2;
    }
}
