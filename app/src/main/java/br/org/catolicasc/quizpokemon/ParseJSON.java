package br.org.catolicasc.quizpokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseJSON {
    public static void main(String[] args) {
        String arquivo = "faturas.json";
        String jsonFaturas = lerArquivo(arquivo);

        List<Fatura> faturas = leFaturasDeJSONString(jsonFaturas);

        double totalFaturas = 0;
        for (Fatura fatura : faturas) {
            totalFaturas += fatura.totalFaturado();
        }

        System.out.printf("> Total das faturas: R$ %.2f (%d faturas)\n", totalFaturas, faturas.size());
    }

    private static String lerArquivo(String arquivo) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linhaAtual;
            while ((linhaAtual = br.readLine()) != null)
            {
                builder.append(linhaAtual).append("\n");
            }
        }
        catch (IOException e) {
            System.err.println("Erro lendo arquivo: " + e.getMessage());
        }

        return builder.toString();
    }

    private static List<Fatura> leFaturasDeJSONString(String jsonString) {
        List<Fatura> listaFaturas = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray faturas = json.getJSONArray("faturas");

            for (int i = 0; i < faturas.length(); i++) {
                JSONObject fatura = faturas.getJSONObject(i);
                Fatura f = new Fatura(
                        fatura.getString("nome"),
                        fatura.getString("id"),
                        fatura.getString("urlImagem"),
                );
                listaFaturas.add(f);
            }
        } catch (JSONException e) {
            System.err.println("Erro fazendo parse de String JSON: " + e.getMessage());
        }

        return listaFaturas;
    }
}


