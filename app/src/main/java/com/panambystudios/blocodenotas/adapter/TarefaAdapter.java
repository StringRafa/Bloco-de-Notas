package com.panambystudios.blocodenotas.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panambystudios.blocodenotas.R;
import com.panambystudios.blocodenotas.model.Tarefa;


import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {


    private List<Tarefa> listaTarefas;


    public TarefaAdapter(List<Tarefa> lista) {

        this.listaTarefas = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View intemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa_adapter,parent,false);

        return new MyViewHolder(intemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());


    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tarefa;
        //ImageView imagem;
        public MyViewHolder(View intemView){
            super(intemView);

            //imagem = itemView.findViewById(R.id.imageView);
            tarefa = itemView.findViewById(R.id.textTarefa);

        }

    }

}
