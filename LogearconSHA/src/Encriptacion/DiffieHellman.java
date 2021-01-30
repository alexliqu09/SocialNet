package Encriptacion;

import java.math.BigInteger;

public class DiffieHellman {
    private int p;
    private int g;
    private int a,b;
    private int A,B;
    private int k;
    
    public DiffieHellman(int p) {
        this.p=p;
        this.g=PrimitiveRoot.run(p);
        this.a=(int)(Math.random()*(p-1));
        this.b=(int)(Math.random()*(p-1));
        
        BigInteger pTemporal= BigInteger.valueOf(p);
        BigInteger gTemporal= BigInteger.valueOf(g);
        BigInteger aTemporal= BigInteger.valueOf(a);
        BigInteger bTemporal =BigInteger.valueOf(b);
        BigInteger nTemporal;
        this.A=gTemporal.modPow(aTemporal, pTemporal).intValue();
        this.B=gTemporal.modPow(bTemporal, pTemporal).intValue();
        this.k=BigInteger.valueOf(B).modPow(aTemporal, pTemporal).intValue();;
        
    }
    
    public void Testeo(){
        System.out.println(A);
        System.out.println(B);
        System.out.println(k+"\n");
    }
    
    public int getA(){
        return this.A;
    }

    public int getK() {
        return k;
    }
    
    public void receiveB(int B){
        this.B=B;
    }
    
    public int generateKey(){
        BigInteger aTemporal= BigInteger.valueOf(a);
        BigInteger pTemporal= BigInteger.valueOf(p);
        this.k=BigInteger.valueOf(B).modPow(aTemporal, pTemporal).intValue();
        return this.k;
    }

    public int getKey() {
        return k;
    }
           
    public static void main(String[] args) {
        int p=72719;
        int g=PrimitiveRoot.run(p);
        
        
        int a=(int)(Math.random()*(p-1));
        int b=(int)(Math.random()*(p-1));
        
        BigInteger pTemporal= BigInteger.valueOf(p);
        BigInteger gTemporal= BigInteger.valueOf(g);
        BigInteger aTemporal= BigInteger.valueOf(a);
        BigInteger bTemporal= BigInteger.valueOf(b);
        
        BigInteger nTemporal;
        int A=gTemporal.modPow(aTemporal, pTemporal).intValue();
        int B=gTemporal.modPow(bTemporal, pTemporal).intValue();
        int k_a= BigInteger.valueOf(B).modPow(aTemporal, pTemporal).intValue();
        int k_b= BigInteger.valueOf(A).modPow(bTemporal, pTemporal).intValue();
        
        
        System.out.println("El primo es: "+p);
        System.out.println("Su raiz primitiva es: "+g);
        System.out.println("Con a: "+a);
        System.out.println("Con A: "+A);
        System.out.println("Con a: "+b);
        System.out.println("Con B: "+B);

        System.out.println("Clave en k_a: "+k_a);
        System.out.println("Clave en k_b: "+k_b);

    }
}
