package Encriptacion;

import java.math.BigInteger;
import java.util.Random;

public class Complemento {
    
    /*
    public static void main(String[] args) {
        int q=4, p=6;
        System.out.println("MCD: "+Complemento.mcd(p, q));
    }
    */
    
    public static int eulerIndicator(int p, int q){
        return (p-1)*(q-1);
    }
    
    public static int mcd(int p, int q){
        if( q==0) return p;
        else return Complemento.mcd(q, p%q);
    }
    
    public static int[] processingMenssage(String Message){
        int[] processedMessage = new int[Message.length()];
        char character;
        int i;
        
        for (i=0; i<Message.length(); i++){
            character=Message.charAt(i);
            if(character==' ') processedMessage[i]=26;
            else processedMessage[i]=(int)character-65;
            //System.out.println(processedMessage[i]);
        }
        return processedMessage;
    }
    
    public static String reprocessingMenssage(int[] decryptedMessage){
        String MessageReceived = new String();
        int i;
        
        for (i=0; i<decryptedMessage.length; i++){
            
            if(decryptedMessage[i]!=26){
                MessageReceived=MessageReceived+String.valueOf((char)(decryptedMessage[i]+65));
            }
            else MessageReceived=MessageReceived+" ";
        }
        return MessageReceived;
    }
    
    /*public static int generatePrime(int length){
        Random rnd=new Random();
        int flag;
        int exp= (int)(Math.log(length)/Math.log(2));
        do{
            flag = BigInteger.probablePrime(exp, rnd).intValue();
        }while(!PrimitiveRoot.isPrime(flag));
        
        return flag;
    }*/
    
    public static int generatePrime(){
        int num;
        while(true){
            num =(int)(Math.random()*9000+Math.pow(10, 2));
            if(isPrime(num)) break;
        }
        return num;
    }
    
    public static boolean isPrime(int x){
        for(int i=2;i<x;i++)
            if(x%i==0) return false;
        return true;
    }
    
}
