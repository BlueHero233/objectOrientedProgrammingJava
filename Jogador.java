import java.io.Serializable;

public class Jogador implements Serializable{
    private final String id;
    private final boolean humano; // true para humano, falso para maquina
    private int saldo;
    private int aposta;
    private JogoDados jogo;

    public Jogador(String id, boolean humano){
        this.id = id;
        this.humano = humano;
        this.saldo = 100;
    }

    public void atualizaSaldo(){
        if(!humano)
            this.aposta = saldo/5;
        this.saldo -= aposta;
    }

    public void rolarDados(){
        System.out.println("Sou o jogador: "+id+" e estou rolando os dados: ");
        jogo.rolarDados();
    }

    public int getSaldo(){
        return saldo;
    }
    public String getId(){  return id; }
    public boolean getHumano(){ return humano; }
    public int getAposta(){ return aposta; }

    public void setAposta(int aposta){ this.aposta = aposta; }
    public void setJogo(int qtd){ this.jogo = new JogoDados(qtd); }
    public void setSaldo(int premio){ this.saldo += premio; }
    public int jogoCraps(){ return jogo.craps(); }
    public int jogoDoble(){ return jogo.doble(); }
}