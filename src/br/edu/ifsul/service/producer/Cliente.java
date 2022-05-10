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
 * Thread responsavel por produzir as mensagens. Esta Thread vai adicionar os Servicos do Cliente na fila onde os Prestadores de Servicos irao consumir.
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araujo
 *
 */
public class Cliente extends Thread {

    private BlockingQueue serviceQueue = null;
    private String nome;
    private Calendar dataCadastro;
    private List<Servico> servicos;
    
    public Cliente(BlockingQueue serviceQueue) {
        servicos = new ArrayList<>();
        this.serviceQueue = serviceQueue;
    }

    /**
     * <p> Método que irá executar a Thread.
     * </p>
     * @since 1.0
     */
    @Override
    public void run() {
        servicos.stream().forEach(servico -> {
            try {
                serviceQueue.put(servico);
            } catch (InterruptedException ex) {
                System.out.println("Ops! Algum erro ocorreu!");
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }

    public BlockingQueue getServiceQueue() {
        return serviceQueue;
    }

    public void setServiceQueue(BlockingQueue serviceQueue) {
        this.serviceQueue = serviceQueue;
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
