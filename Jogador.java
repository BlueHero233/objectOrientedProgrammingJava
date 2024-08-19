public class Jogador{
    private String id;
    private boolean tipo;
    private double saldo;
    private double aposta;
    private JogoDados jogo;

    public Jogador(String id, int qtd){
        this.id = id;
        this.jogo = new JogoDados(qtd);
    }
    public double aumentarSaldo(){
        saldo += aposta;
        return saldo;
    }
    public double diminuirSaldo(){
        saldo -= aposta;
        return saldo;
    }
    public void mostrarSaldoAtual() { System.out.println(saldo); }
    public void inicializaDadosJogador(String nId, double vSaldo){
        id = nId;
        saldo = vSaldo;
    }
    public double getSaldo(){
        return saldo;
    }
    public void rolarDados(){
        System.out.println("Sou o jogador: "+id+" e estou rolando os dados: ");
        jogo.rolarDados();
    }
    public void apostar(){

    }
    public void mostrarLancamentosJogo(){

    }
    @Override
    public String toString(){
        System.out.println("Sou um dado rolando");
        return id + " " + jogo.toString();
    }
}