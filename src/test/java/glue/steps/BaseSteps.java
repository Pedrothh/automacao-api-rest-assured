package glue.steps;

import com.google.gson.JsonElement;
import net.datafaker.Faker;
import utils.Token;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;

public class BaseSteps {

    protected static final String token = Token.getAccessToken();
    protected static final String baseURL = "https://my-public-api-for-tests-production.up.railway.app/api";

    protected static Response response;

    protected static Faker faker = new Faker(new Locale("pt-BR"));
    protected Map<String, String> queryParams = new HashMap<>();

    BaseSteps(){
        RestAssured.baseURI = baseURL;
    }

    protected Response enviarRequisicaoGet(JsonObject payload) {
        PrintStream logStream = System.out;
        return given()
                .filter(new RequestLoggingFilter(logStream))
                .filter(new AllureRestAssured())
                .header("accept", "application/json")
                .header("Authorization", token)
                .queryParams(queryParams)
                .body(payload.toString())
                .when().get()
                .then().log().all().extract().response();
    }

    protected Response enviarRequisicaoPost(JsonObject payload) {
        PrintStream logStream = System.out;
        return given()
                .filter(new RequestLoggingFilter(logStream))
                .filter(new AllureRestAssured())
                .header("Authorization", token)
                .contentType("application/json")
                .body(payload.toString())
                .when().post()
                .then().log().all().extract().response();
    }

    protected Response enviarRequisicaoPut(JsonObject payload) {
        PrintStream logStream = System.out;
        return given()
                .filter(new RequestLoggingFilter(logStream))
                .filter(new AllureRestAssured())
                .header("Authorization", token)
                .contentType("application/json")
                .body(payload.toString())
                .when().put()
                .then().log().all().extract().response();
    }

    protected Response enviarRequisicaoDelete(JsonObject payload) {
        PrintStream logStream = System.out;
        return given()
                .filter(new RequestLoggingFilter(logStream))
                .filter(new AllureRestAssured())
                .header("Authorization", token)
                .contentType("application/json")
                .body(payload.toString())
                .when().delete()
                .then().log().all().extract().response();
    }

    protected static JsonObject alterarTags(JsonObject jsonObject, Map<String, String> tagsAlvo) {
        Map<String, Supplier<Object>> geradores = Map.of(
                "password", () -> faker.number().digits(9),
                "empty", () -> ""
        );

        for (String chave : jsonObject.keySet()) {
            JsonElement elemento = jsonObject.get(chave);

            if (tagsAlvo.containsKey(chave)) {
                // Substituindo o valor se a chave estiver nas tags-alvo
                String tipo = tagsAlvo.get(chave);
                if (geradores.containsKey(tipo)) {
                    addPropertyGeneric(jsonObject, chave, geradores.get(tipo).get());
                }
            } else if (elemento.isJsonObject()) {
                // Recursivamente verificar objetos aninhados
                alterarTags(elemento.getAsJsonObject(), tagsAlvo);
            }
        }
        return jsonObject;
    }

    protected JsonObject lerArquivoJson(String caminhoArquivo) throws IOException {
        Path path = Paths.get(caminhoArquivo).toAbsolutePath();
        if (Files.exists(path)) {
            String content = Files.readString(path);
            return JsonParser.parseString(content).getAsJsonObject();
        }
        return new JsonObject(); // Retorna um JsonObject vazio caso o arquivo não exista
    }

    public static void addPropertyGeneric(JsonObject jsonObject, String property, Object value) {
        if (value instanceof String) {
            jsonObject.addProperty(property, (String) value);
        } else if (value instanceof Number) {
            jsonObject.addProperty(property, (Number) value);
        } else if (value instanceof Boolean) {
            jsonObject.addProperty(property, (Boolean) value);
        } else if (value instanceof Character) {
            jsonObject.addProperty(property, (Character) value);
        } else {
            // Caso genérico: converte o valor em uma String (pode ajustar conforme necessário)
            jsonObject.addProperty(property, value != null ? value.toString() : null);
        }
    }
}
