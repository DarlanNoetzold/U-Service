package br.edu.ifsul.service.model;

import br.edu.ifsul.service.producer.Cliente;
/**
 * Classe de Modelo para as informacoes de Servico.
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araujo
 *
 */
public class Servico {
    private String nome;
    private Cliente cliente;
    private double valor;

    public Servico(String nome, Cliente cliente) {
        this.nome = nome;
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return cliente.getNome() + " - " + nome;
    }
}
