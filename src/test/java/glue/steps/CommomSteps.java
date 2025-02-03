package glue.steps;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.util.ISO8601Utils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CommomSteps extends BaseSteps {

    static String metodo, pastaJsons;

    @Dado("que tenho um token válido")
    public void queTenhoUmTokenValido() {
        Assert.assertNotNull(token);
        Assert.assertNotEquals("", token);
    }

    @Dado("^que o endpoint (.*) \"([^\"]*)\" está disponível$")
    public void queOEndpointEstaDisponivel(String metodo, String endpoint) {
        RestAssured.basePath = endpoint;
        CommomSteps.pastaJsons = endpoint.replaceAll("[{}]", "");
        CommomSteps.metodo = metodo;
    }

    @Quando("enviar a requisição GET")
    public void enviarRequisicaoGet() throws IOException {
        String caminhoArquivo = "src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/request.json";
        JsonObject payload = lerArquivoJson(caminhoArquivo);
        response = enviarRequisicaoGet(payload);
    }

    @Dado("^que o endpoint (\\w+) \"([^\"]*)\" está disponível com o[s]? parâmetro[s]? (.*)$")
    public void queOEndpointEstaDisponivelComParametros(String metodo, String endpoint, String parametros) throws IOException {
        CommomSteps.pastaJsons = endpoint.replaceAll("[{}]", "");
        CommomSteps.metodo = metodo;
        RestAssured.basePath = substituirPlaceholders(endpoint, parametros);
    }

    private String substituirPlaceholders(String endpoint, String parametros) {
        List<String> listaParametros = Arrays.stream(parametros.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        for (String param : listaParametros) {
            endpoint = endpoint.replaceFirst("\\{[^}]*}", param);
        }
        return endpoint;
    }


    @Quando("enviar a requisição POST")
    public void enviarRequisicaoPost() throws IOException {
        String caminhoArquivo = "src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/request.json";
        JsonObject payload = lerArquivoJson(caminhoArquivo);
        response = enviarRequisicaoPost(payload);
    }

    @Quando("enviar a requisição POST com tags alteradas")
    public void enviarRequisicaoPost(Map<String, String> tagsAlterar) throws IOException {
        String caminhoArquivo = "src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/request.json";
        JsonObject payload = lerArquivoJson(caminhoArquivo);
        alterarTags(payload, tagsAlterar);
        response = enviarRequisicaoPost(payload);
    }

    @Quando("enviar a requisição PUT")
    public void enviarRequisicaoPut() throws IOException {
        String caminhoArquivo = "src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/request.json";
        JsonObject payload = lerArquivoJson(caminhoArquivo);
        response = enviarRequisicaoPut(payload);
    }

    @Quando("enviar a requisição DELETE")
    public void enviarRequisicaoDelete() throws IOException {
        String caminhoArquivo = "src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/request.json";
        JsonObject payload = lerArquivoJson(caminhoArquivo);
        response = enviarRequisicaoDelete(payload);
    }

    @Entao("o código de resposta deve ser {int}")
    public void oCodigoDeRespostaDeveSer200(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @E("a mensagem da resposta deve ser {string}")
    public void aMensagemDaRespostaDeveSer(String mensagem) {
        String responseBody = response.getBody().asString();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
        String message = jsonObject.get("message").getAsString();

        Assert.assertEquals(mensagem, message);
    }

    @Entao("a estrutura de resposta deve estar correta")
    public void aEstruturaDeRespostaDeveEstarCorreta() {
        File jsonSchema = new File(Paths.get("").toAbsolutePath() + "/src/test/java/jsonSchema" + CommomSteps.pastaJsons + "/" + CommomSteps.metodo + "/response.json");
        response
                .then()
                .assertThat()
                .body(matchesJsonSchema(jsonSchema)).log().all();
    }
}
