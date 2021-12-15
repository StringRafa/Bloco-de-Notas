package com.panambystudios.blocodenotas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {

    private Long id;
    private String nomeTarefa;
    //private String anotacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    /*
    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

     */
}
