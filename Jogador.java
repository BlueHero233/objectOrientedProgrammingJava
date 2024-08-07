public class Jogador{
    private String id;
    private JogoDados jogo;
    public Jogador(String id, int qtd){
        this.id = id;
        this.jogo = new JogoDados(qtd);
    }
    public void rolarDados(){
        System.out.println("Sou o jogador: "+id+" e estou rolando os dados: ");
        jogo.rolarDados();
    }
}