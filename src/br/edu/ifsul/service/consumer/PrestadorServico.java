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
 * Thread consumidora da fila de Serviços
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araújo
 * @version 1.0
 */
public class PrestadorServico extends Thread{
    private String nome;
    private Calendar dataCadastro;
    private List<Servico> servicosRealizar;
    
    private BlockingQueue serviceQueue = null;

    private Menu menu;

    public PrestadorServico(BlockingQueue serviceQueue, Menu menu) {
        servicosRealizar = new ArrayList<>();
        this.serviceQueue = serviceQueue;
        this.menu = menu;
    }
    /**
     * <p> Método que irá executar a Thread.
     * </p>
     * @since 1.0
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
        try {
            int tam = serviceQueue.size();
            while(tam > 0){
                Servico servico = (Servico) serviceQueue.take();
                System.out.println("Você deseja realizar o servico " + servico.getNome() + " do Cliente "+servico.getCliente().getNome()+"? [0-não|1-sim]");
                String op = scanner.nextLine();
                if(op.equals("1")) servicosRealizar.add(servico);
                else serviceQueue.put(servico);
                tam=tam-1;
            }
            printServicos(this, serviceQueue);
            menu.getPrestadoresServicos().add(this);
            synchronized (getMenu()){
                getMenu().notify();
            }
         } catch (Exception e){
            System.out.println("Ops! Ocorreu algum erro!");
            e.printStackTrace();
        }

    }
    /**
     * <p> Método que irá mostrar os Serviços que o atual Prestador de Serviços aceitou realizar e os Serviços que permanecem na fila.
     * </p>
     * @param ps Prestador de Serviços com as informações necessárias para mostrar para o usuário.
     * @param serviceQueue Fila de Serviços.
     * @since 1.0
     */
    private void printServicos(PrestadorServico ps, BlockingQueue serviceQueue){
        System.out.println("Serviços do Prestador de Serviços " + ps.getNome());
        ps.getServicosRealizar().forEach(servico -> System.out.println(servico.getCliente().getNome() + " - " + servico.getNome()));
        System.out.println("=================");
        System.out.println("Serviços na fila:");
        serviceQueue.forEach(System.out::println);
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
