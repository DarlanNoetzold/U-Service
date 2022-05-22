/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.edu.ifsul.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * Classe main de inicializacao do projeto.
 * @author Darlan Noetzold
 * @author Jakelyny Sousa de Araujo
 *
 */
public class UService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BlockingQueue serviceQueue = new ArrayBlockingQueue(1024);

        Menu menu = new Menu(serviceQueue);

        new Thread(menu).start();

    }
}
