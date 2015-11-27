package com.kg.matheus.guiavd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 18/08/2015.
 */
public class JsonFeiticos {

    public String getJson(Context c) throws IOException {
        String urlString = "http://valediagonal.qlix.com.br/feiticos.php";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        String saida="";
        while ((output = br.readLine()) != null) {
            saida=saida+output;
        }
        conn.disconnect();
            return saida;
    }

    public List<Feitico> jsonParseFeitico(String jsonString) {
        List<Feitico> feiticos = new ArrayList<Feitico>();
        try {
            JSONArray feiticosJson = new JSONArray(jsonString);
            JSONObject feitico;
            for (int i = 0; i < feiticosJson.length(); i++) {
                feitico = new JSONObject(feiticosJson.getString(i));
                Feitico objetoFeitico = new Feitico();
                objetoFeitico.setId(Integer.parseInt(feitico.getString("ID")));
                objetoFeitico.setNome(feitico.getString("nome"));
                objetoFeitico.setDescricao(feitico.getString("descricao"));
                feiticos.add(objetoFeitico);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return feiticos;
    }

    public boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
