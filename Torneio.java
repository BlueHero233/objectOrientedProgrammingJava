public class Torneio{
    public static void main(String[] args){
        Dado dado1 = new Dado();
        Dado dado2 = new Dado();
        Jogador jog = new Jogador();
        jog.inicializaDadosJogador(100,0);
        jog.mostrarSaldoAtual();
        while(jog.getSaldo()>0){
            int somaDados = dado1.sortearNumero()+dado2.sortearNumero();
            if(somaDados==7||somaDados==11){
                jog.aumentarSaldo();
            }
            else if(somaDados==2||somaDados==3||somaDados==12){
                jog.diminuirSaldo();
            }
            else{
                int valorPonto = somaDados;
                do {
                    somaDados=dado1.sortearNumero()+dado2.sortearNumero();
                } while(somaDados!=7&&somaDados!=valorPonto);
                if(somaDados==7){
                    jog.diminuirSaldo();
                } else {
                    jog.aumentarSaldo();
                }
            }
            jog.mostrarSaldoAtual();
        }
    }
}