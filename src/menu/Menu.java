package menu;

import service.ExchangeRateService;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private final String apiKey;
    private final Scanner scanner;
    private final ExchangeRateService exchangeRateService;

    public Menu(String apiKey) {
        this.apiKey = apiKey;
        this.scanner = new Scanner(System.in);
        this.exchangeRateService = new ExchangeRateService(apiKey);
    }

    public boolean validarApi() throws IOException, InterruptedException {
        return exchangeRateService.validarApi();
    }

    public void exibirMenu() throws IOException, InterruptedException {
        System.out.println("""
                Lista de Moedas para conversão
                
                USD - Dólar Americano     
                EUR - Euro
                JPY - Iene Japonês
                GBP - Libra Esterlina     
                CAD - Dólar Canadense
                CHF - Franco Suíço
                CNY - Yuan Chinês
                AUD - Dólar Australiano
                BRL - Real Brasileiro
                MXN - Peso Mexicano     
                
                """);

        System.out.print("Primeiramente insira a moeda que deseja converter:  ");
        String moedaDe = scanner.nextLine().toUpperCase();

        System.out.print("Agora diga a moeda que deseja saber o valor convertido: ");
        String moedaPara = scanner.nextLine().toUpperCase();

        System.out.print("Digite o valor em " + moedaDe + " que deseja saber em " + moedaPara + ": ");
        double valor = Double.parseDouble(scanner.nextLine());

        exchangeRateService.obterConversao(moedaDe, moedaPara, valor);
    }

    public String escolherOpcao() {
        System.out.print("Deseja encerrar o programa? ");
        return scanner.nextLine();
    }
}


