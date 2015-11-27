package com.kg.matheus.guiavd;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.List;

import static com.kg.matheus.guiavd.R.*;

public class TelaFeiticos extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(layout.tela_feiticos);


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            setTitle("Livro de Feitiços");

            //setando cor e colocando icone na actionbar
            android.support.v7.app.ActionBar action = getSupportActionBar();
            action.setDisplayUseLogoEnabled(true);
            action.setDisplayShowHomeEnabled(true);
            action.setLogo(R.drawable.logo);
            //action.setBackgroundDrawable(getResources().getDrawable(logo));
            action.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));



            lista = (ListView) findViewById(android.R.id.list);

            Controlador crud = new Controlador(getBaseContext());

            if (verificaConexao()) {
                crud.reiniciarTabelaFeitico();

                //atualiza o BD local
                JsonFeiticos atualizador = new JsonFeiticos();
                List<Feitico> temp = atualizador.jsonParseFeitico(atualizador.getJson(getBaseContext()));

                for (Feitico f : temp) {
                    crud.insereFeitico(f);
                }
            }

                List<Feitico> feiticos = crud.getAllFeiticos(getBaseContext());
                String[] nomes = new String[feiticos.size()];
                int i = 0;

                for (Feitico feitico : feiticos) {
                    nomes[i] = feitico.getNome();
                    i++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, id.text1, nomes);
                // Assign adapter to ListView
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent it = new Intent(TelaFeiticos.this, MostraFeitico.class);
                        it.putExtra("ID", position);
                        startActivity(it);
                    }
                });


        }catch(IOException e){


        }
        }



    public boolean verificaConexao(){
        Context contexto = getBaseContext();
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);//Pego a conectividade do contexto o qual o metodo foi chamado

        NetworkInfo netInfo = cm.getActiveNetworkInfo();//Crio o objeto netInfo que recebe as informacoes da NEtwork

        if ( (netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()) ) //Se o objeto for nulo ou nao tem conectividade retorna false
            return true;
        else
            return false;

    }


}
