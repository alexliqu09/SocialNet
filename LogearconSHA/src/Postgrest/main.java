/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Postgrest;
import Encriptacion.RSA;
/**
 *
 * @author alex
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 Postgres pos= new Postgres();
        pos.guardar(20129195,"nada",1);
      //Postgres post= new Postgres();
      //post.guardar("20192196","hola",1);
    }
    
}
