import java.util.Random;

public class Dado{
    private int numero;
    private Random rnd = new Random();
    public int sortearNumero(){
        numero = rnd.nextInt(6) + 1;
        return numero;
    }
    public void roll(){
        System.out.println("Sou um dado rolando");
    }
    public int getNumero(){
        return numero;
    }
    public String toString(){
        return "Numero: " + numero;
    }
}