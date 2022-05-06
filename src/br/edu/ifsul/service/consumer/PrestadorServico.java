/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.service.consumer;

import br.edu.ifsul.service.Menu;
import br.edu.ifsul.service.model.Servico;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author 20201PF.CC0149
 */
public class PrestadorServico extends Thread{
    private String nome;
    private Calendar dataCadastro;
    private List<Servico> servicosRealizar;
    
    private BlockingQueue queue = null;

    private Menu menu;

    public PrestadorServico(BlockingQueue queue, Menu menu) {
        servicosRealizar = new ArrayList<>();
        this.queue = queue;
        this.menu = menu;
    }
    
    public void run(){
        Scanner scanner = new Scanner(System.in);
        try {
            int tam = queue.size();
            while(tam > 0){
                Servico servico = (Servico) queue.take();
                System.out.println("Você deseja realizar o servico " + servico.getNome() + " do Cliente "+servico.getCliente().getNome()+"? [0-não|1-sim]");
                String op = scanner.nextLine();
                if(op.equals("1")) servicosRealizar.add(servico);
                else queue.put(servico);
                tam=tam-1;
            }
            printServicos(this, queue);
            menu.getPrestadoresServicos().add(this);
            synchronized (getMenu()){
                getMenu().notify();
            }
         } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void printServicos(PrestadorServico ps, BlockingQueue queue){
        System.out.println("Serviços do Prestador de Serviços " + ps.getNome());
        ps.getServicosRealizar().forEach(servico -> System.out.println(servico.getCliente().getNome() + " - " + servico.getNome()));
        System.out.println("=================");
        System.out.println("Serviços na fila:");
        queue.forEach(System.out::println);
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

    public List<Servico> getServicosRealizar() {
        return servicosRealizar;
    }

    public void setServicosRealizar(List<Servico> servicosRealizar) {
        this.servicosRealizar = servicosRealizar;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
