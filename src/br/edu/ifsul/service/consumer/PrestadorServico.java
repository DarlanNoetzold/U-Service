/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.service.consumer;

import br.edu.ifsul.service.Menu;
import br.edu.ifsul.service.model.Servico;
import br.edu.ifsul.service.producer.Cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread consumidora da fila de Servicos
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araujo
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
     * <p> Metodo que ira executar a Thread.
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
         } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Ops! Algum erro ocorreu!", ex);
        }

    }
    /**
     * <p> Metodo que ira mostrar os Servicos que o atual Prestador de Servicos aceitou realizar e os Servicos que permanecem na fila.
     * </p>
     * @param ps Prestador de Serviços com as informacoes necessárias para mostrar para o usuario.
     * @param serviceQueue Fila de Servicos.
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
