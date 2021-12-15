package com.panambystudios.blocodenotas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panambystudios.blocodenotas.R;
import com.panambystudios.blocodenotas.adapter.TarefaAdapter;
import com.panambystudios.blocodenotas.helper.RecyclerItemClickListener;
import com.panambystudios.blocodenotas.helper.TarefaDAO;
import com.panambystudios.blocodenotas.model.Tarefa;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;
    private boolean isDecorated;
    //private List<Tarefa> listaAnotacoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaTarefas(){
        //Listar Tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        //AnotacaoDAO anotacaoDAO = new AnotacaoDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();
        //listaAnotacoes = anotacaoDAO.listar();

        //Exibe a lista de tarefas no RecyclerView

        //configurar adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);


        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //Recuperando tarefa para edição
                                Tarefa tarefaSelecionada = listaTarefas.get(position);
                                //Enviar tarefa para tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this,AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada",tarefaSelecionada);

                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar tarefa para deletar
                                tarefaSelecionada = listaTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //Configurar o titulo da mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a anotação: " + tarefaSelecionada.getNomeTarefa() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                        if(tarefaDAO.deletar(tarefaSelecionada)){
                                            carregarListaTarefas();
                                            isDecorated = false;
                                            Toast.makeText(getApplicationContext(),
                                                    "Sucesso ao excluir anotação!",
                                                    Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao excluir anotação!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );


        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if(!isDecorated){
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            isDecorated = true;
        }
        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}