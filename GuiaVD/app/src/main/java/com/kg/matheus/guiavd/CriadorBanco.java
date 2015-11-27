package com.kg.matheus.guiavd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matheus on 17/08/2015.
 */
public class CriadorBanco extends SQLiteOpenHelper {

    public CriadorBanco(Context context){
        super(context, "vd_App", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ "feiticos" + " (id INTEGER PRIMARY KEY, nome TEXT, descricao TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ "user" + " (id INTEGER, username TEXT, lat INTEGER, lon INTEGER, first INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //onUpgrade function
        db.execSQL("DROP TABLE IF EXISTS " + "feiticos");
        db.execSQL("DROP TABLE IF EXISTS " + "user");
        onCreate(db);
    }
}
