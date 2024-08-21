import java.util.Random;
import java.io.Serializable;

public class Dado implements Serializable {
    private int numero;

    public void sortearNumero(){
        Random rnd = new Random();
        numero = rnd.nextInt(6) + 1;
    }
    public int getNumero(){ return numero; }
}