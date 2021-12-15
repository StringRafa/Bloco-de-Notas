package com.panambystudios.blocodenotas.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";
    public static String ANOTACAO = "anotacao";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabela1 = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL); ";


        String tabela2 = "CREATE TABLE IF NOT EXISTS " + ANOTACAO +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL); ";



        try{
            db.execSQL(tabela1);
            Log.i("INFO DB","Sucesso ao criar a tabela1");
        }catch (Exception e){
            Log.i("INFO DB","Erro ao criar a tabela" + e.getMessage());
        }

        try{
            db.execSQL(tabela2);
            Log.i("INFO DB","Sucesso ao criar a tabela2");
        }catch (Exception e){
            Log.i("INFO DB","Erro ao criar a tabela" + e.getMessage());
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String tabela1 = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";
        String tabela2 = "DROP TABLE IF EXISTS " + ANOTACAO + " ;";
        try{
            db.execSQL(tabela1);
            db.execSQL(tabela2);
            onCreate(db);
            Log.i("INFO DB","Sucesso ao atualizar App");
        }catch (Exception e){
            Log.i("INFO DB","Erro ao atualizar App" + e.getMessage());
        }

    }
}
