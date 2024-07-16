import menu.Menu;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira sua API:");
        String apiKey = scanner.nextLine();

        Menu menu = new Menu(apiKey);

        try {
            if (!menu.validarApi()) {
                System.out.println("API inválida. Encerrando o programa.");
                return;
            }

            System.out.println("API validada com sucesso.");
            System.out.println();

            menu.exibirMenu();

            String opcao;
            do {
                opcao = menu.escolherOpcao();
                if (opcao.equalsIgnoreCase("s") || opcao.equalsIgnoreCase("sim")) {
                    break;
                }
                menu.exibirMenu();
            } while (true);

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao acessar a API: " + e.getMessage());
        } finally {
            scanner.close();
        }

        System.out.println("Programa encerrado.");
    }
}
