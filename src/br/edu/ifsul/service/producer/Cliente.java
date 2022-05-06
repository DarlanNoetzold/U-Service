/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.service.producer;

import br.edu.ifsul.service.model.Servico;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20201PF.CC0149
 */
public class Cliente extends Thread {

    private BlockingQueue queue = null;
    private String nome;
    private Calendar dataCadastro;
    private List<Servico> servicos;
    
    public Cliente(BlockingQueue queue) {
        servicos = new ArrayList<>();
        this.queue = queue;
    }
    
    @Override
    public void run() {
        servicos.stream().forEach(servico -> {
            try {
                queue.put(servico);
            } catch (InterruptedException ex) {
                System.out.println("Ops! Algum erro ocorreu!");
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }

    public BlockingQueue getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue queue) {
        this.queue = queue;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Servico> getServico() {
        return servicos;
    }

    public void setServico(List<Servico> servico) {
        this.servicos = servico;
    }

    
    
    
}
