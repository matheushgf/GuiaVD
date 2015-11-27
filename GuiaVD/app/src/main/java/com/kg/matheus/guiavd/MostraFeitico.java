package com.kg.matheus.guiavd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MostraFeitico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostra_feitico);

        android.support.v7.app.ActionBar action = getSupportActionBar();
        action.setDisplayUseLogoEnabled(true);
        action.setDisplayShowHomeEnabled(true);
        action.setLogo(R.drawable.logo);
        action.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            int id = bundle.getInt("ID");
            Controlador crud = new Controlador(getBaseContext());
            Feitico atual = crud.getFeitico(id+1);
            setTitle(atual.getNome());
            TextView title = (TextView)findViewById(R.id.feitico_title);
            TextView descr = (TextView)findViewById(R.id.feitico_descricao);
            title.setText(atual.getNome());
            descr.setText(atual.getDescricao());
            }
    }

}
