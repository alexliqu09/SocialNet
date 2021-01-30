/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Encriptacion.Complemento;
import javax.swing.*;


public class Main {
    
    public static void main(String args[]) {
        Pantalla pant1, pant2;
        int prime = Complemento.generatePrime();//ERROR
        
        pant1 = new Pantalla(prime); pant1.setVisible(true);            
        pant2 = new Pantalla(prime); pant2.setVisible(true);

        pant1.setReceptor(pant2);
        pant2.setReceptor(pant1);
        pant2.diffie.Testeo();
        pant1.inicializateRSA();
        pant2.inicializateRSA();
    }
}
