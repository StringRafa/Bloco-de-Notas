package com.panambystudios.blocodenotas.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.panambystudios.blocodenotas.model.Tarefa;


import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escreve.insert(DbHelper.TABELA_TAREFAS,null, cv);
            //escreve.insert(DbHelper.ANOTACAO,null, cv);
            Log.i("INFO","TAREFA SALVA COM SUCESSO!");
        }catch (Exception e){
            Log.e("INFO","ERRO AO SALVAR TAREFA!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.update(DbHelper.TABELA_TAREFAS, cv , "id=?", args);
            Log.i("INFO","Tarefa atualizada com sucesso!!");
        }catch (Exception e){
            Log.e("INFO","ERRO ao atualizar tarefa!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DbHelper.TABELA_TAREFAS,"id=?",args);
            Log.i("INFO","Anotação removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO","ERRO ao remover anotação!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String tabela1 = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(tabela1,null);

        while(c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString( c.getColumnIndex("nome") );

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);

        }

        return tarefas;
    }
}
