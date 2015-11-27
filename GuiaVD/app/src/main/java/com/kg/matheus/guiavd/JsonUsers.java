package com.kg.matheus.guiavd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;
import java.util.Random;
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
 * Created by Math on 26/11/2015.
 */
public class JsonUsers {

    Random r = new Random();

    public String getJson(Context c) throws IOException {
        String urlString = "http://valediagonal.qlix.com.br/services.php?type=getusers";
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

    public List<User> jsonParseUser(String jsonString) {
        List<User> users = new ArrayList<User>();
        try {
            JSONArray usersJson = new JSONArray(jsonString);
            JSONObject user;
            for (int i = 0; i < usersJson.length(); i++) {
                user = new JSONObject(usersJson.getString(i));
                User objetoUser = new User();
                objetoUser.setId(Integer.parseInt(user.getString("id")));
                objetoUser.setUsername(user.getString("username"));
                objetoUser.setLat(Double.parseDouble(user.getString("lat")));
                objetoUser.setLon(Double.parseDouble(user.getString("lon")));
                users.add(objetoUser);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return users;
    }

    public void updateUser(String username, double lat, double lon, Context c) {
        try{
        String urlString = "http://valediagonal.qlix.com.br/services.php?type=updateuser&username="+username+"&lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
    }catch(Exception e){
            Toast t = Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public void createUser(String username, double lat, double lon, Context c){
        try{
            String urlString = "http://valediagonal.qlix.com.br/services.php?type=newuser&username="+username+"&lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&id="+r.nextInt(999);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  boolean verificaConexao(Context context) {
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
