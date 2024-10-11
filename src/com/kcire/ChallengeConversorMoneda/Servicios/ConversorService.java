package com.kcire.ChallengeConversorMoneda.Servicios;

import com.google.gson.Gson;
import com.kcire.ChallengeConversorMoneda.Entidades.ConversionDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class ConversorService {
    private Scanner entrada;
    //utilizando una variable de entorno para ocultar la apikey
    private static final String API_KEY = System.getenv("API_KEY");


    public ConversorService() {
        this.entrada = new Scanner(System.in);

    }

    public ConversionDTO buscaMoneda(String base, String target, double amount) {

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/"+ API_KEY + "/pair/" + base + "/" + target + "/" + amount);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // Realizamos la solicitud HTTP y deserializamos la respuesta en el DTO
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), ConversionDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("No se encontro esa moneda");
        }
    }

    public void mostrarConversion(String base, String target, double amount) {
        ConversionDTO conversion = buscaMoneda(base, target, amount);
        if ("success".equalsIgnoreCase(conversion.result())) {
            System.out.println(amount + " [" + base + "] equivalen a " + conversion.conversion_result() + " [" + target + "]");
            System.out.println("***********************************************************");
        } else {
            System.out.println("Error en la conversion");
        }
    }

    public void menu() {
        int opcion = 0;
        do {
            System.out.println("""
                    ***CONVERSOR DE MONEDAS***
                    
                    1. Dólar => Peso argentino
                    2. Peso argentino => Dólar
                    3. Dólar => Real brasileño
                    4. Real brasileño => Dólar
                    5. Dólar => Peso colombiano
                    6. Peso colombiano => Dólar
                    7. Salir
                    """);
            System.out.println("Ingrese una opción:");
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch (opcion) {
                case 1 -> convertir("USD", "ARS");
                case 2 -> convertir("ARS", "USD");
                case 3 -> convertir("USD", "BRL");
                case 4 -> convertir("BRL", "USD");
                case 5 -> convertir("USD", "COP");
                case 6 -> convertir("COP", "USD");
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 7);

    }

    public void convertir(String base, String target) {
        System.out.println("***********************************************************");
        System.out.println("Ingrese la cantidad de " + base + " que desea convertir:");
        double amount = entrada.nextDouble();
        entrada.nextLine();
        mostrarConversion(base, target, amount);
    }

}
