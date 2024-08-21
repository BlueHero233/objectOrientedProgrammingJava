import java.io.Serializable;
public class JogoDados implements Serializable{
    private final Dado[] dados;

    public JogoDados(int qtd){
        dados = new Dado[qtd];
        for(int i=0;i<dados.length; i++) dados[i] = new Dado();
    }

    public void rolarDados(){ for (Dado dado : dados) dado.sortearNumero(); }

    public int craps(){
        dados[0].sortearNumero();
        dados[1].sortearNumero();
        int dado1 = dados[0].getNumero();
        int dado2 = dados[1].getNumero();
        int craps = dado1+dado2;
        System.out.println("Soma obtida: " + craps);
        if (craps == 7 || craps == 11)
            return 1;
        else if (craps == 2 || craps == 3 || craps == 12)
            return -1;
        else{
            int point = craps;
            while (true){
                craps = 0;
                dados[0].sortearNumero();
                dados[1].sortearNumero();
                int dado1 = dados[0].getNumero();
                int dado2 = dados[1].getNumero();
                craps += dado1+dado2;
                if (craps == point)
                    return 1;
                else if (craps == 2 || craps == 3 || craps == 12)
                    return -1;
            }
        }
    }
    public int doble(){
        int dobles = 0;
        int tentativas = 0;
        while (dobles<300){
            tentativas++;
            dados[0].sortearNumero();
            dados[1].sortearNumero();
            int dado1 = dados[0].getNumero();
            int dado2 = dados[1].getNumero();
            System.out.println(dado1 + "+" + dado2);
            dobles += dado1 == dado2 ? dado1 == 1 ? 30 : (dado1 * dado2) * 2 : (dado1 * dado2);
            System.out.println("Pontos atuais: " + dobles);
        }
        return tentativas;
    }
}