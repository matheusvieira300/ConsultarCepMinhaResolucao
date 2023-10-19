import Endereco.EnderecoRecord;
import Endereco.Endereco;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws  IOException {
        Scanner leitura = new Scanner(System.in);

        List<Endereco> enderecos = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting().create();



        System.out.println("Digite um CEP: ");
        String leituraDoCep = leitura.nextLine();
        String enderecoDaApi = "https://viacep.com.br/ws/" + leituraDoCep + "/json/";

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(enderecoDaApi)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String Json = response.body();
            System.out.println("Json começa aqui: " + Json);

            EnderecoRecord enderecoRecord = gson.fromJson(Json, EnderecoRecord.class);
            System.out.println("enderecorecord "+ enderecoRecord);
            Endereco enderecoPraGuardar = new Endereco(enderecoRecord);
            enderecos.add(enderecoPraGuardar);

            System.out.println(enderecos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        FileWriter escritaJson = new FileWriter("cep.json");
          escritaJson.write(gson.toJson(enderecos));
            escritaJson.close();
        System.out.println("Encerrado");


    }
}