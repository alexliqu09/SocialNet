/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Postgrest;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author alex
 */
public class Conectar {
String bd;
String usurio;
String password;
    public Conectar(){
        this.bd = "jdbc:postgresql://localhost:5432/socialnet";
        this.usurio="alexander";
        this.password="312014";
        
    }
    
      public Connection conectar(){
        try{
            Connection conectar= DriverManager.getConnection(bd, usurio, password);
            System.out.println("Exito al conectar");
            return conectar;
        }catch(Exception e){
            System.out.println("Mal");
            return null;
        }
    }
}
