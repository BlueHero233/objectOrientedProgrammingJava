import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Torneio{
    private Jogador[] jogadores; //vetor de jogadores
    private int numJogadores; //quantidade de jogadores
    private static final int capacidadeMaxima = 10;

    public Torneio(){ //inicializa o torneio com 0 jogadores e capacidade maxima de 10
        this.jogadores = new Jogador[capacidadeMaxima];
        this.numJogadores = 0;
    }
    public int getNumJogadores() { return this.numJogadores; } //retorna quantidade de jogadores
    public void adicionarJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) { //verifica se nao excede 10 jogadores
            jogadores[numJogadores++] = jogador; //incrementa o indice
            System.out.println("Jogador " + jogador.getId() + " Adicionado com sucesso!");
        } else System.out.println("Jogador não adicionado :(");
    }
    public void removerJogador(String id) {
        for (int i = 0; i < numJogadores; i++) { //percorre o vetor de jogadores
            if (jogadores[i].getId().equals(id)) { //se achar o id
                jogadores[i] = jogadores[numJogadores-1]; //joga o ultimo jogador para o indice achado
                jogadores[numJogadores-1] = null; //remove o jogador
                numJogadores--; //diminiu o tamanho do vetor
                System.out.println("Jogador " + id + " Removido com sucesso!");
                break; //sai do laco for
            }
            else { System.out.println("Jogador " + id + "não encontrado :("); } //se nao achar, mensagem de erro
        }
    }
    public void gravarArquivo(){ //cria um arquivo
        File arquivo = new File("Arquivo.dat");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( arquivo );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );
            objectOutputStream.writeObject(jogadores);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Arquivo " + arquivo.getName() + " gravado com sucesso!");
        } catch (Exception e) { System.out.println(" Erro ao gravar arquivo :("); }
    }
    public void lerArquivo(){ //le um arquivo
        File arquivo = new File("Arquivo.dat");
        try{
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            jogadores = (Jogador[]) objectInputStream.readObject();
            numJogadores = jogadores.length;
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Arquivo " + arquivo.getName() + " lido com sucesso!");
        } catch (Exception e) { System.out.println("Erro ao ler arquivo :("); }
    }
    public void iniciarTorneio(){
        int rodadas = 1; //contador de rodadas
        boolean ganhador = false;
        for (int i = 0; i < numJogadores; i++) jogadores[i].setSaldo(100); //inicia o saldo toda vez que o torneio comeca
        boolean fimTorneio = false;
        Scanner entrada = new Scanner(System.in); //entrada teclado
        System.out.println("Escolha um jogo");
        System.out.println("1 - Jogo de Azar;");
        System.out.println("2 - Jogo do Porquinho;");
        int opcao = entrada.nextInt();
        entrada.nextLine();
        while (!fimTorneio){
            System.out.println("Rodada " + rodadas++ );
            switch (opcao){
                case 1:
                    while (!ganhador){
                        int valor = 0;
                        int numGanhadores = 0;
                        int[] ganhadores = new int[numJogadores];
                        int indice = 0;
                        pedirAposta(entrada); //funcao para pedir aposta cada jogador
                        for (int i = 0; i < numJogadores; i++) {
                            if (jogadores[i].getSaldo()>0){ //verifica se tem saldo positivo
                                jogadores[i].setJogo(2); //adiciona dois dados no jogo
                                jogadores[i].rolarDados(); //rola os dados
                                if (jogadores[i].jogoCraps()==1){ //retorno de jogoCraps é 1 se o jogador venceu
                                    System.out.println(jogadores[i].getId() + " ganhou!");
                                    ganhadores[indice++]=i;
                                    numGanhadores++;
                                    ganhador = true;
                                }
                                else System.out.println("Jogador " + jogadores[i].getId() + " perdeu :(");
                            }
                            else System.out.println("Jogador nao tem saldo :(");
                        }
                        int maiorAposta = 0;
                        if (numGanhadores>0){
                            int numGanhadoresMaiorAposta = 0;
                            for (int i = 0; i < numGanhadores; i++) {
                                int j = ganhadores[i];
                                int apostaAux = jogadores[j].getAposta();
                                if (apostaAux>maiorAposta){
                                    maiorAposta = apostaAux;
                                    numGanhadoresMaiorAposta = 1;
                                }else if (maiorAposta==apostaAux) numGanhadoresMaiorAposta++;
                            }
                            for (int i = 0; i < numJogadores; i++) {
                                boolean ganhadorMaiorAposta = false;
                                for (int j = 0; j < numGanhadores; j++) {
                                    if (ganhadores[j]==i && jogadores[i].getAposta()==maiorAposta){
                                        ganhadorMaiorAposta = true;
                                        break;
                                    }
                                }
                                if (!ganhadorMaiorAposta && jogadores[i].getSaldo()>0){
                                    jogadores[i].atualizaSaldo();
                                    valor += jogadores[i].getAposta();
                                }
                            }
                            if (numGanhadoresMaiorAposta>0){
                                int premio = valor/numGanhadoresMaiorAposta;
                                for (int i = 0; i < numGanhadores; i++) {
                                    int j = ganhadores[i];
                                    if (jogadores[j].getAposta()==maiorAposta){
                                        jogadores[j].setSaldo(premio);
                                    }
                                }
                            }
                        }else System.out.println("Todos os jogadores perderam :(");
                        System.out.println("Fim da Rodada");
                        for (int i = 0; i < numJogadores; i++) {
                            boolean ganhadorMaiorAposta = false;
                            for (int j = 0; j < numGanhadores; j++) {
                                if (ganhadores[j]==i && jogadores[i].getAposta()==maiorAposta){
                                    ganhadorMaiorAposta = true;
                                    break;
                                }
                            }
                            System.out.println(ganhadorMaiorAposta ? jogadores[i].getId() + " Ganhou!" : "Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                        } break;
                    } break;
                case 2:
                    while(!ganhador){
                        int valor = 0;
                        int [] tentativas = new int[numJogadores];
                        pedirAposta(entrada);
                        for (int i = 0; i < numJogadores; i++) {
                            if (jogadores[i].getSaldo()>0){
                                jogadores[i].setJogo(2);
                                System.out.println("Jogador " + jogadores[i].getId() + " rolando os dados:");
                                tentativas[i]=jogadores[i].jogoDoble();
                                System.out.println("Valor Atingido");
                            }else jogadores[i].setAposta(0);
                        }
                        int menor = Integer.MAX_VALUE;
                        for (int i = 0; i < numJogadores; i++) if (tentativas[i] < menor && tentativas[i] != 0) menor = tentativas[i];
                        int numGanhadores = 0;
                        int[] ganhadores = new int[numJogadores];
                        for (int i = 0; i < numJogadores; i++) if (tentativas[i] == menor) ganhadores[numGanhadores++] = i;

                        int maiorAposta = 0;
                        int numGanhadoresMaiorAposta = 0;
                        for (int i = 0; i < numGanhadores; i++) {
                            int j = ganhadores[i];
                            int apostaAux = jogadores[j].getAposta();
                            if (apostaAux>maiorAposta){
                                maiorAposta = apostaAux;
                                numGanhadoresMaiorAposta = 1;
                            }else if (maiorAposta==apostaAux) numGanhadoresMaiorAposta++;
                        }
                        for (int i = 0; i < numJogadores; i++) {
                            ganhador = false;
                            for (int j = 0; j < numGanhadores; j++) {
                                if (ganhadores[j]==i && jogadores[i].getAposta()==maiorAposta){
                                    ganhador = true;
                                    break;
                                }
                            }
                            if (!ganhador && jogadores[i].getSaldo()>0){
                                jogadores[i].atualizaSaldo();
                                valor += jogadores[i].getAposta();
                            }
                        }
                        if (numGanhadoresMaiorAposta>0){
                            int premio = valor/numGanhadoresMaiorAposta;
                            for (int i = 0; i < numGanhadores; i++) if (jogadores[ganhadores[i]].getSaldo()==maiorAposta) jogadores[ganhadores[i]].setSaldo(premio);
                        }
                        ganhador = true;
                        System.out.println("Fim da Rodada");
                        for (int i = 0; i < numJogadores; i++) {
                            if(tentativas[i]!=0){
                                System.out.println("Total de tentativas de " + jogadores[i].getId() + ": " + tentativas[i]);
                                System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                            }
                        }
                        for (int i = 0; i < numJogadores; i++) for (int j = 0; j < numGanhadores; j++) if (ganhadores[j] == i && jogadores[ganhadores[j]].getAposta() == maiorAposta) System.out.println("Jogador " + jogadores[i].getId() + " Ganhou a rodada!");
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + opcao);
            }
            int jogadoresAtivos = 0;
            for (int i = 0; i < numJogadores; i++) if (jogadores[i].getSaldo() > 0) jogadoresAtivos++;

            if(jogadoresAtivos==1){
                fimTorneio = true;
                rodadas -= 1;
                System.out.println("Resultado Final");
                System.out.println("Rodadas jogadas: " + rodadas);
                for (int i = 0; i < numJogadores; i++) System.out.println(jogadores[i].getSaldo() > 0 ? "Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo() + "Ganhador!" : "Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
            }
        }
    }

    public void pedirAposta(Scanner scanner) {
        for (int i = 0; i < numJogadores; i++) {
            if (jogadores[i].getSaldo()>0){
                if (jogadores[i].getHumano()){
                    System.out.println("Valor da aposta de " + jogadores[i].getId() + ": ");
                    int aposta = scanner.nextInt();
                    scanner.nextLine();
                    jogadores[i].setAposta(aposta);
                }
            }
        }
    }
}