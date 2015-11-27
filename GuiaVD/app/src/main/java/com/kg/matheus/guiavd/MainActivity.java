package com.kg.matheus.guiavd;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;


import static com.kg.matheus.guiavd.R.*;

public class MainActivity extends AppCompatActivity {

    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(layout.activity_main);

            android.support.v7.app.ActionBar action = getSupportActionBar();
            action.setDisplayUseLogoEnabled(true);
            action.setDisplayShowHomeEnabled(true);
            action.setLogo(drawable.logo);
            action.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));

            lista = (ListView) findViewById(id.listMain);
            String[] opcoes = new String[]{"Livro de feitiços", "Encontre um bruxo!"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout.list_item, id.text1, opcoes);
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    if(position==0) {
                        Intent it = new Intent(MainActivity.this, TelaFeiticos.class);
                        startActivity(it);
                    }else if(position==1) {
                        Intent it = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(it);
                    }
                }
            });

        }catch (Exception e){
            Toast t = new Toast(getApplicationContext());
            t.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }

   public void onClick(View view){

    }
}
