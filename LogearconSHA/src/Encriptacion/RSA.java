package Encriptacion;

import Postgrest.Postgres;
import java.math.BigInteger;
import java.util.Random;

public class RSA {
    
    private int p, q, n, indicator;
    public int publicKey,privateKey; 
    
    private int[] processedMessage;
    private int[] encryptedMessage;
    private int[] decryptedMessage;//solo util para la funcion testing
    private int[] encryptedMessageReceived;
    private int[] decryptedMessageReceived;
    
    
    private int secondPublicKey, secondN;
    
    private String Message;
    private String MessageReceived;
    
    public RSA(){ //ERROR
        
        
        this.processedMessage = null;
        this.encryptedMessage = null;
        this.decryptedMessage = null;
        DiffieHellman diff;
        do{
            diff = new DiffieHellman(Complemento.generatePrime());
            this.p=233;
            this.q=283;
            this.n = p*q;
            this.indicator=Complemento.eulerIndicator(this.p, this.q);
            if(indicator <= diff.getK()) continue; 
        }while(Complemento.mcd(diff.getK(), this.indicator)!=1);
        
        this.publicKey=diff.getK();
        this.privateKey=RSA.generatePrivateKey(this.publicKey, this.indicator);
    }
    
    public RSA(int p, int q, int dhk){
        this.p=p;
        this.q=q;
        this.n = p*q;
        
        this.processedMessage = null;
        this.encryptedMessage = null;
        this.decryptedMessage = null;
        
        this.indicator=Complemento.eulerIndicator(this.p, this.q);
        this.publicKey=dhk;
        this.privateKey=RSA.generatePrivateKey(this.publicKey, this.indicator);
    }
    
    public RSA(int p, int q, int publicKey, int privateKey){
        this.p=p;
        this.q=q;
        this.n = p*q;
        
        this.processedMessage = null;
        this.encryptedMessage = null;
        this.decryptedMessage = null;
        
        this.indicator=Complemento.eulerIndicator(this.p, this.q);
        this.publicKey=publicKey;
        this.privateKey=privateKey;
    }
    
    public void preparate(int secondPublicKey, int secondN){
        this.secondPublicKey = secondPublicKey; 
        this.secondN = secondN;
    }
    
    public void testing(String Message){
                
        this.processedMessage = Complemento.processingMenssage(Message);
        this.encryptedMessage = RSA.encrypt(this.processedMessage, this.publicKey, this.n);
        this.decryptedMessage = RSA.decrypt(this.encryptedMessage, this.privateKey, this.n);
        this.MessageReceived = Complemento.reprocessingMenssage(this.decryptedMessage);
        this.Message=Message;
        
        System.out.println("El mensaje recibido es: "+Message);
        System.out.print("\nEn numeros seria: ");
        for (int i:this.processedMessage) System.out.print(i+" ");
        
        System.out.println("\n\nLa encriptacion letra por letra seria: ");
        for (int i=0; i<this.Message.length(); i++) {
            System.out.println("["+(i+1)+"]: "+this.processedMessage[i]+"^"+this.publicKey+"%"+this.n+" = "+this.encryptedMessage[i]);
        }
        
        System.out.println("\n\nLa desencriptacion letra por letra seria: ");
        for (int i=0; i<this.Message.length(); i++) {
            System.out.println("["+(i+1)+"]: "+this.encryptedMessage[i]+"^"+this.privateKey+"%"+this.n+" = "+this.decryptedMessage[i]);
        }
        
        System.out.println("\n\nEl mensaje recibido seria: "+MessageReceived);
    }
    
    
    public String initializationTesting(){
        StringBuilder testing = new StringBuilder();
        
        testing.append("primo p: "+p+"\n");
        testing.append("primo q: "+q+"\n");
        testing.append("n: "+n+"\n");
        testing.append("clave publica: "+publicKey+"\n");
        testing.append("clave publica: "+privateKey+"\n");
        testing.append("n del receptor: "+secondN+"\n");
        testing.append("clave publica del receptor: "+secondPublicKey+"\n");
        
        return testing.toString();
    }

    public int getPublicKey() {
        return publicKey;
    }

    public int getN() {
        return n;
    }
           
    public int[] prepareMessage(String Message,String usuario){
        this.Message=Message;
        this.processedMessage=Complemento.processingMenssage(this.Message);
        this.encryptedMessage=RSA.encrypt(processedMessage, secondPublicKey, secondN);

        String message=guardar_message(this.encryptedMessage);
        System.out.println("no imprimes :"+message);
        Postgres pos = new Postgres();
        String id_nombre=pos.select("id_nombre","nombre", usuario);
        int id_nom=Integer.parseInt(id_nombre);
        pos.guardar(id_nom,message, 1);
  
        return this.encryptedMessage;
  
        }
   
    public String reciveEncryptedMessage(int[] encryptedMessageReceived){
        this.encryptedMessageReceived=encryptedMessageReceived;
        this.decryptedMessageReceived=RSA.decrypt(encryptedMessageReceived, privateKey, n);
        this.MessageReceived=Complemento.reprocessingMenssage(this.decryptedMessageReceived);
        return this.MessageReceived;
    }
    public String guardar_message(int [] miarray){
        String cadena="/";
        for (int x=0;x<miarray.length;x++){
        cadena =cadena+","+(miarray[x]);
        //System.out.println(miarray[x]);    
        }
        String cadena_normal=cadena.substring(5,cadena.length());
        System.out.println(cadena_normal);
        return cadena;
    }
    
    public String encryptionTesting(String usuario){
        StringBuilder testing = new StringBuilder();
        
        testing.append("El mensaje recibido es: "+Message);
        testing.append("\nEn numeros seria: ");
        for (int i:this.processedMessage) testing.append(i+" ");
        
        testing.append("\nLa encriptacion letra por letra seria: ");
        for (int i=0; i<this.Message.length(); i++) {
            testing.append("\n["+(i+1)+"]: "+this.processedMessage[i]+"^"+this.secondPublicKey+"%"+this.secondN+" = "+this.encryptedMessage[i]);
        }
        
        testing.append("\nEl mensaje encriptado seria: ");
        for (int i:this.encryptedMessage) testing.append(i+" ");
        
        testing.append("\n");
        /*
        String message=guardar_message(this.encryptedMessage);
        System.out.println("no imprimes :"+message);
        Postgres pos = new Postgres();
        String id_nombre=pos.select("id_nombre","nombre", usuario);
        int id_nom=Integer.parseInt(id_nombre);
        pos.guardar(id_nom,message, 1);
   */
        return testing.toString();
   
        }
    
        
    public String decryptionTesting(){
        StringBuilder testing = new StringBuilder();
        
        testing.append("El mensaje encriptado recibido es: ");
        for (int i:this.encryptedMessageReceived) testing.append(i+" ");
        
        testing.append("\nLa desencriptacion letra por letra seria: ");
        for (int i=0; i<this.decryptedMessageReceived.length; i++){
            testing.append("\n["+(i+1)+"]: "+this.encryptedMessageReceived[i]+"^"+this.privateKey+"%"+this.n+" = "+this.decryptedMessageReceived[i]);
        }
        
        testing.append("\nEl mensaje recibido desencriptado seria: "+MessageReceived+"\n");
        return testing.toString();
    }
    
    public static int[] encrypt(int[] processedMessage, int publicKey, int n){
        
        BigInteger number;
        BigInteger nTemporal= BigInteger.valueOf(n);
        int[] encryptedMessage = new int[processedMessage.length];
        
        for (int i=0; i<processedMessage.length; i++){
            number = BigInteger.valueOf(processedMessage[i]);
            number = number.pow(publicKey);
            number = number.mod(nTemporal);
            encryptedMessage[i] = number.intValue();
        }
        return encryptedMessage;
    }
    
    public static int[] decrypt(int[] encryptedMessage, int privateKey, int n){
        
        BigInteger number;
        BigInteger nTemporal= BigInteger.valueOf(n);
        int[] decryptedMessage = new int[encryptedMessage.length];
        
        for (int i=0; i<encryptedMessage.length; i++){
            number = BigInteger.valueOf(encryptedMessage[i]);
            number = number.pow(privateKey);
            number = number.mod(nTemporal);
            decryptedMessage[i] = number.intValue();
        }
        return decryptedMessage;
    }
    
    public static int generatePublicKey(int indicator){
        int publicKey;
        
        do{
            publicKey = (int)(Math.random()*(indicator-2))+2;
        }while(Complemento.mcd(indicator, publicKey)!=1);
        
        return publicKey;
    }
    
    public static int generatePrivateKey(int publicKey, int indicador){
        //int privateKey;
        BigInteger publicKeyTemporal= BigInteger.valueOf(publicKey);
        BigInteger indicadorTemporal= BigInteger.valueOf(indicador);
        BigInteger privateKeyTemporal;
        
        new BigInteger(32, new Random());
        
        do privateKeyTemporal = new BigInteger(16, new Random());
        while(privateKeyTemporal.multiply(publicKeyTemporal).mod(indicadorTemporal).intValue()!=1);
        
        return privateKeyTemporal.intValue();
    }
}

/*
    int a=2,b=500, number;
            while(true){
            number=Math.random()*(b-a+1)+a;
            if(verificarN(number)  && n!=number) break;
            }
    */

