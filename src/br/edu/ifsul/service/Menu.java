package br.edu.ifsul.service;

import br.edu.ifsul.service.consumer.PrestadorServico;
import br.edu.ifsul.service.model.Servico;
import br.edu.ifsul.service.producer.Cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Menu extends Thread {
    private BlockingQueue queue;

    private List<PrestadorServico> prestadoresServicos;

    public Menu(BlockingQueue queue) {
        this.queue = queue;
        prestadoresServicos = new ArrayList<>();
    }

    public void run(){
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Para ir a area do Cliente digite 1, para ir a area do Prestador de Serviços digite 2. Ou 0 para sair!");
            String ans = scanner.nextLine();
            if(ans.equals("1")) areaCliente(scanner, queue);
            else if(ans.equals("2")) {
                areaPrestadorDeServicos(scanner, queue);
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if(ans.equals("0")){
                printAll();
                break;
            }else System.out.println("Não temos essa opção!");
        }
    }

    public void areaCliente(Scanner scanner, BlockingQueue queue){
        System.out.println("Bem vindo ao U-service, precisamos de algumas infomações: ");
        Cliente cliente = new Cliente(queue);
        System.out.print("Qual o seu nome: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Quantos serviços você deseja contratar? ");
        int quant=1;
        try {
            quant = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Valor Inválido! Será cadastrado apenas um serviço.");
        }
        int cont=0;
        while(quant > cont){
            System.out.println("Qual(s) serviço você deseja contratar? ");
            Servico servico = new Servico(scanner.nextLine(), cliente);

            cliente.getServico().add(servico);
            cont += 1;
        }

        new Thread(cliente).start();
    }

    public void areaPrestadorDeServicos(Scanner scanner, BlockingQueue queue){
        System.out.println("Area do Prestador de Servico!");
        PrestadorServico ps = new PrestadorServico(queue, this);
        System.out.print("Qual o seu nome: ");
        ps.setNome(scanner.nextLine());
        ps.setDataCadastro(Calendar.getInstance());

        new Thread(ps).start();
    }

    private void printAll() {
        System.out.println("PRESTADORES DE SERVIÇOS");
        getPrestadoresServicos().forEach(prestadorServico -> {
            System.out.println("===================================================");
            System.out.println("Serviços do prestador "+ prestadorServico.getNome());
            prestadorServico.getServicosRealizar().stream().forEach(System.out::println);
        });
        System.out.println("===================================================");
        System.out.println("Serviços na fila:");
        queue.forEach(System.out::println);
    }


    public BlockingQueue getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue queue) {
        this.queue = queue;
    }

    public List<PrestadorServico> getPrestadoresServicos() {
        return prestadoresServicos;
    }

    public void setPrestadoresServicos(List<PrestadorServico> prestadoresServicos) {
        this.prestadoresServicos = prestadoresServicos;
    }
}
