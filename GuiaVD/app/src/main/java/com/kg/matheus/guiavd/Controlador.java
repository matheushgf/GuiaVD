package com.kg.matheus.guiavd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Matheus on 17/08/2015.
 */
public class Controlador {

    private SQLiteDatabase db;
    private CriadorBanco banco;


    public Controlador(Context context){
        banco = new CriadorBanco(context);
    }

    public void reiniciarTabelaFeitico(){
        db = banco.getWritableDatabase();
        db.execSQL("DROP TABLE feiticos");
        db.execSQL("CREATE TABLE feiticos(id INTEGER PRIMARY KEY, nome TEXT, descricao TEXT)");
    }

    public List<Feitico> getAllFeiticos(Context context) {
        List<Feitico> feiticosList = new ArrayList<Feitico>();
        // Select All Query
        String selectQuery = "SELECT  * FROM feiticos";

        db = banco.getWritableDatabase();
        Cursor cursor;
        String[] campos = {"id","nome","descricao"};
        cursor = db.query("feiticos",campos, null, null, null, null, null, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Feitico feitico = new Feitico();
                feitico.setId(Integer.parseInt(cursor.getString(0)));
                feitico.setNome(cursor.getString(1));
                feitico.setDescricao(cursor.getString(2));
                // Adiciona o feitico na lista
               feiticosList.add(feitico);
            } while (cursor.moveToNext());
        }

        // return lista de feiticos
        return feiticosList;
    }

    public Feitico getFeitico(int id) {
       db = banco.getReadableDatabase();

        Cursor cursor = db.query("feiticos", new String[] { "id",
                        "nome", "descricao" }, "id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Feitico feitico = new Feitico();
        feitico.setId(Integer.parseInt(cursor.getString(0)));
        feitico.setNome(cursor.getString(1));
        feitico.setDescricao(cursor.getString(2));
        // return contact
        return feitico;
    }


    public void insereFeitico(Feitico feitico){
        ContentValues valores = new ContentValues();
        long resultado;

        db = banco.getWritableDatabase();

        String sql= String.format("INSERT INTO feiticos VALUES(%d, '%s', '%s')",feitico.getId(), feitico.getNome(), feitico.getDescricao());
        db.execSQL(sql);
    }

    public void reiniciarTabelaUser(Context context){
        db = banco.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("CREATE TABLE user(id INTEGER, username TEXT, lat INTEGER, lon INTEGER, first INTEGER)");
    }

    public String getUsername(Context context){
        db = banco.getReadableDatabase();
        String[] campos = {"id", "username", "lat", "long"};
        Cursor cursor = db.query("user", campos, null, null, null, null, null);
        String username="";
        if (cursor.moveToFirst()) {
                username=cursor.getString(1);
        }
        return username;
    }

}
