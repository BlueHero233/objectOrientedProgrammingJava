import java.util.Random;

public class Dado{
    private int numero;

    public void sortearNumero(){
        System.out.println("Sou um dado rolando");
        Random rnd = new Random();
        numero = rnd.nextInt(6) + 1;
    }
    public int getNumero(){
        return numero;
    }
}