package br.edu.ifsul.service;

import br.edu.ifsul.service.consumer.PrestadorServico;
import br.edu.ifsul.service.model.Servico;
import br.edu.ifsul.service.producer.Cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
/**
 * Thread responsavel por criar e mostrar o Menu de escolhas para o usuario.
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araujo
 *
 */
public class Menu extends Thread {
    private BlockingQueue serviceQueue;

    private List<PrestadorServico> prestadoresServicos;

    public Menu(BlockingQueue serviceQueue) {
        this.serviceQueue = serviceQueue;
        prestadoresServicos = new ArrayList<>();
    }
    /**
     * <p> Metodo que ira executar a Thread. Mostrando o Menu enquanto o(s) usuario(s) precisar.
     * </p>
     * @since 1.0
     */
    public void run(){
        try {
            label:
            while(true){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Para ir a area do Cliente digite 1, para ir a area do Prestador de Serviços digite 2, se você quer ver a agenda completa digite 3. Ou 0 para sair!");
                String ans = scanner.nextLine();

                switch (ans) {
                    case "1":
                        areaCliente(scanner, serviceQueue);
                        break;
                    case "2":
                        areaPrestadorDeServicos(scanner, serviceQueue);
                        break;
                    case "3":
                        System.out.println("==================AGENDA COMPLETA==================");
                        printAll();
                        break;
                    case "0":
                        printAll();
                        break label;
                    default:
                        System.out.println("Não temos essa opção!");
                        break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Ops! Ocorreu um erro!");
            e.printStackTrace();
        }
    }
    /**
     * <p> Metodo que ira capturar as informacoes do Cliente e Servico(s) e enviar para a Thread do mesmo.
     * </p>
     * @param scanner Scanner que ira capturar as informacoes que o usuario (Cliente) digitar.
     * @param serviceQueue Fila de Servicos que será enviado para a Thread de Cliente.
     * @since 1.0
     */
    public void areaCliente(Scanner scanner, BlockingQueue serviceQueue) {
        Cliente cliente = new Cliente(serviceQueue);

        System.out.println("Bem vindo ao U-service, precisamos de algumas infomações: ");
        System.out.print("Qual o seu nome: ");
        cliente.setNome(scanner.nextLine());

        System.out.println("Quantos serviços você deseja contratar? ");
        int quant = 0;
        while(true) {
            try {
                quant = Integer.parseUnsignedInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Valor Inválido! >:( Digite um número positivo!");
                quant=-1;
            }
            if(quant != -1) break;
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

    /**
     * <p> Metodo que ira capturar as informacoes do Prestador de Servicos e enviar para a Thread do mesmo.
     * </p>
     * @param scanner Scanner que irá capturar as informacoes que o usuario (Prestador de Serviços) digitar.
     * @param serviceQueue Fila de Servicos que sera enviado para a Thread de Prestador de Servicos.
     * @since 1.0
     */
    public void areaPrestadorDeServicos(Scanner scanner, BlockingQueue serviceQueue) throws InterruptedException {
        PrestadorServico ps = new PrestadorServico(serviceQueue, this);

        System.out.println("Area do Prestador de Servico!");
        System.out.print("Qual o seu nome: ");
        ps.setNome(scanner.nextLine());
        ps.setDataCadastro(Calendar.getInstance());

        new Thread(ps).start();
        synchronized (this) {
            wait();
        }
    }

    /**
     * <p> Metodo que ira mostrar todos os Servicos com seus respectivos Prestadores de Servicos e Clientes, alem daqueles Servicos que permaneceram na fila.
     * </p>
     * @since 1.0
     */
    private void printAll() {
        System.out.println("PRESTADORES DE SERVIÇOS");
        getPrestadoresServicos().forEach(prestadorServico -> {
            System.out.println("===================================================");
            System.out.println("Serviços do prestador "+ prestadorServico.getNome());
            prestadorServico.getServicosRealizar().stream().forEach(System.out::println);
        });
        System.out.println("===================================================");
        System.out.println("Serviços na fila:");
        serviceQueue.forEach(System.out::println);
    }


    public BlockingQueue getServiceQueue() {
        return serviceQueue;
    }

    public void setServiceQueue(BlockingQueue serviceQueue) {
        this.serviceQueue = serviceQueue;
    }

    public List<PrestadorServico> getPrestadoresServicos() {
        return prestadoresServicos;
    }

    public void setPrestadoresServicos(List<PrestadorServico> prestadoresServicos) {
        this.prestadoresServicos = prestadoresServicos;
    }
}
