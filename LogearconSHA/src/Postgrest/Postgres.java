/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Postgrest;

import java.sql.*;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author alex
 */
public class Postgres {
String bd;
String usurio;
String password;

    public Postgres(){
        /*
        this.bd = "jdbc:postgresql://localhost:5432/socialnet";
        this.usurio="alexander";
        this.password="312014";
        */
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Postgres postgres = new Postgres();
        postgres.select("password","nombre","Alexander Lique");
       
    }
        public String select(String colummna,String columna_condicionte,String usuario){
        Conectar con = new Conectar();
        String sql ="select "+ colummna +" from usuario where "+columna_condicionte+" ='"+usuario+"'";
        String dato =null;
        String contrasena;
        Statement st;
        try{
          st =  con.conectar().createStatement();
          ResultSet result = st.executeQuery(sql);
          while(result.next()){
              dato=result.getString(colummna);
          }
          System.out.println("el dato extraido: " +dato);
          con.conectar().close();
          st.close();
          System.out.println("se logro dar la consulta");
          return dato;
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            System.out.print("no se logro dar la consulta");
            return null;
        }
    }
       public String pedir_primo(String cod_user,String tabla,String colummna_condicionada,int id_nombre){
           Conectar con = new Conectar();
           String sql="select "+cod_user+" from "+ tabla + " where "+colummna_condicionada+" = "+id_nombre+";";
           String dato =null;
           Statement st;
           try{
          st =  con.conectar().createStatement();
          ResultSet result = st.executeQuery(sql);
          while(result.next()){
              dato=result.getString(cod_user);
          }
          System.out.println("el dato extraido: " +dato);
          con.conectar().close();
          st.close();
          System.out.println("se logro dar la consulta");
          return dato;
           }catch(Exception e){
               JOptionPane.showConfirmDialog(null, e);
           }
           return "";
       }
/*
select id_sala from sala_usuario where sala_usuario.id_nombre=20192196 
intersect
select id_sala from sala_usuario where sala_usuario.id_nombre=40229394;
 */    
 public String intersect(String usuario_emisor,String usuario_receptor){
        Conectar con = new Conectar();
        String sql ="select id_sala from sala_usuario where sala_usuario.id_nombre="+usuario_receptor +" intersect select id_sala from sala_usuario where sala_usuario.id_nombre= "+usuario_emisor+ "; ";
        String dato =null;
        String contrasena;
        Statement st;
        try{
          st =  con.conectar().createStatement();
          ResultSet result = st.executeQuery(sql);
          while(result.next()){
              dato=result.getString("id_sala");
          }
          System.out.println("el dato extraido: " +dato);
          con.conectar().close();
          st.close();
          System.out.println("se logro dar la consulta");
          return dato;
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            System.out.print("no se logro dar la consulta");
            return null;
        }       
      }

public void guardar(int id_usuario,String mensaje,int id_tipo){
    Conectar con = new Conectar();
    Date date = new Date();
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String hora =hourFormat.format(date);
   String fecha= dateFormat.format(date).toString();
    System.out.println(hourFormat.format(date)+" "+dateFormat.format(date));
    try {
    java.sql.Statement st =  con.conectar().createStatement();
    String sql="insert into mensaje(id_usuario,mensaje,id_tipo,fecha,hora) values "+" ("+id_usuario+",'"+mensaje+"',"+id_tipo+",'"+fecha+"','"+hora+"');";
    int result = st.executeUpdate(sql);
    con.conectar().close();
    st.close();
    System.out.println("ok");
            }catch(Exception e){
                System.out.println("surgió un error de tipo: "+e);
            }
   
    
}

}
