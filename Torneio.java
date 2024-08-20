import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Torneio{
    private Jogador[] jogadores;
    private int numJogadores;
    private static final int capacidadeMaxima = 10;
    public Torneio(){
        jogadores = new Jogador[capacidadeMaxima];
        numJogadores = 0;
    }
    public void adicionarJogador(Jogador j) {
        if (numJogadores < jogadores.length) {
            jogadores[numJogadores++] = j;
            System.out.println("Jogador " + j.getId() + " Adicionado com sucesso!");
        } else {
            System.out.println("Jogador não adicionado :(");
        }
    }
    public void removerJogador(String id) {
        boolean achou = false;
        for (int i = 0; i < numJogadores; i++) {
            if (jogadores[i].getId().equals(id)) {
                achou = true;
                jogadores[i] = jogadores[numJogadores-1];
                jogadores[numJogadores-1] = null;
                numJogadores--;
                System.out.println("Jogador " + id + " Removido com sucesso!");
                break;
            }
        }
        if (!achou) { System.out.println("Jogador " + id + "não encontrado :("); }
    }
    public int getNumJogadores() { return numJogadores; }
    public void gravarArquivo(String nomeArquivo){
        File arquivo = new File(nomeArquivo);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( arquivo );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );
            objectOutputStream.writeObject(jogadores);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Arquivo " + nomeArquivo + " gravado com sucesso!");
        } catch (Exception e) { System.out.println(" Erro ao gravar arquivo :("); }
    }
    public void lerArquivo(String nomeArquivo){
        File arquivo = new File(nomeArquivo);
        try{
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            jogadores = (Jogador[]) objectInputStream.readObject();
            numJogadores = jogadores.length;
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Arquivo " + nomeArquivo + " lido com sucesso!");
        } catch (Exception e) { System.out.println("Erro ao ler arquivo :("); }
    }
    public void imprimirSaldoInicial(){
        System.out.println("Saldo Inicial");
        for (int i = 0; i < numJogadores; i++) {
            System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
        }
    }
    public void iniciarTorneio(){
        int rodadas = 1;
        for (int i = 0; i < numJogadores; i++) {
            jogadores[i].reiniciarSaldo();
        }
        imprimirSaldoInicial();
        boolean fimTorneio = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha um jogo");
        System.out.println("1 - Jogo de Azar;");
        System.out.println("2 - Jogo do Porquinho;");
        int opcao = scanner.nextInt();
        while (!fimTorneio){
            System.out.println("Rodada " + rodadas++ );
            switch (opcao){
                case 1:
                    boolean ganhou = false;
                    while (!ganhou){
                        int valor = 0;
                        int numGanhadores = 0;
                        int[] ganhadores = new int[numGanhadores];
                        int indice = 0;
                        for (int i = 0; i < numJogadores; i++) {
                            if (jogadores[i].getSaldo()>0){
                                if (jogadores[i].getHumano()==true){
                                    System.out.println("Valor da aposta de " + jogadores[i].getId() + ": ");
                                    int aposta = scanner.nextInt();
                                    scanner.nextLine();
                                    jogadores[i].setAposta(aposta);
                                }
                            }
                        }
                        for (int i = 0; i < numJogadores; i++) {
                            if (jogadores[i].getSaldo()>0){
                                jogadores[i].setJogo(2);
                                jogadores[i].rolarDados();
                                if (jogadores[i].craps()==1){

                                }
                            }
                        }
                    }
            }
        }
    }
    public void mostrarPlacarFinal(){

    }
    public void mostrarFinalRodada(){

    }
}