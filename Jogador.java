public class Jogador{
    private String id;
    private final boolean humano; // true para humano, falso para maquina
    private double saldo;
    private double aposta;
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
    public double aumentarSaldo(){
        saldo += aposta;
        return saldo;
    }
    public double diminuirSaldo(){
        saldo -= aposta;
        return saldo;
    }
    public void rolarDados(){
        System.out.println("Sou o jogador: "+id+" e estou rolando os dados: ");
        jogo.rolarDados();
    }
    public void mostrarSaldoAtual() { System.out.println(saldo); }
    public void reiniciarSaldo() { this.saldo = 100; }
    public double getSaldo(){
        return saldo;
    }
    public String getId(){  return id; }
    public boolean getHumano(){ return humano; }
    public double getAposta(){ return aposta; }

    public void setAposta(int aposta){ this.aposta = aposta; }
    public void setJogo(int qtd){ this.jogo = new JogoDados(qtd); }
}