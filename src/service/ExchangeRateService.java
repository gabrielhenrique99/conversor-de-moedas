package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {
    private final String apiKey;
    private final HttpClient client;
    private final Gson gson;

    public ExchangeRateService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public boolean validarApi() throws IOException, InterruptedException {
        String url = criarUrl("BRL");
        HttpResponse<String> response = enviarRequisicao(url);
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        return !json.has("error-type");
    }

    public void obterConversao(String moedaDe, String moedaPara, double valor) throws IOException, InterruptedException {
        String url = criarUrl(moedaDe);
        HttpResponse<String> response = enviarRequisicao(url);
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        JsonObject rates = json.getAsJsonObject("conversion_rates");

        if (rates != null && rates.has(moedaPara) && valor > 0) {
            double taxaDeConversao = rates.get(moedaPara).getAsDouble();
            System.out.println("A conversão atual da moeda é: " + taxaDeConversao);
            System.out.printf("%.2f %s equivale a %.2f %s\n", valor, moedaDe, (valor * taxaDeConversao), moedaPara);
            System.out.printf("1 %s equivale a %.2f %s\n", moedaDe, taxaDeConversao, moedaPara);
        } else if (rates == null) {
            System.out.println("Taxa de conversão inválida");
        } else if (valor <= 0) {
            System.out.println("Valor negativo ou menor igual a zero");
        }
    }

    private String criarUrl(String moeda) {
        return "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + moeda;
    }

    private HttpResponse<String> enviarRequisicao(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

