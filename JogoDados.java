public class JogoDados{
    private Dado[] dados;
    private int qtdDados;
    
    public JogoDados(int qtd){
        this.qtdDados = qtd;
        dados = new Dado[this.qtdDados];
        for(int i=0;i<dados.length; i++)
            dados[i]=new Dado();
        
    }
    public void rolarDados(){
        for(int i=0; i<dados.length; i++)
            dados[i].roll();
    }
}