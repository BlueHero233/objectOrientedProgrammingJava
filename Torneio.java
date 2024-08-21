import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Torneio {
    private Jogador[] jogadores; //vetor de jogadores
    private int numJogadores; //quantidade de jogadores
    private static final int capacidadeMax = 10;

    public Torneio() { //inicializa o torneio com 0 jogadores e capacidade 10
        this.jogadores = new Jogador[capacidadeMax];
        this.numJogadores = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) { //verifica se nao excede 10 jogadores
            jogadores[numJogadores++] = jogador; //incrementa o indice
            System.out.println("Jogador " + jogador.getId() + " adicionado.");
        } else {
            System.out.println("Não é possível adicionar mais jogadores.");
        }
    }

    public void removerJogador(String id) {
        for (int i = 0; i< numJogadores; i++) { //percorre o vetor de jogadores
            if (jogadores[i].getId().equals(id)) { //se achar o id
                jogadores[i] = jogadores[numJogadores -1]; //passa o ultimo jogador para a posicao do jogador removido
                jogadores[numJogadores -1] = null; //remove o jogador
                numJogadores--; //diminiu o tamanho do vetor
                System.out.println("Jogador " + id + " removido com sucesso.");
                break;
            }
            else System.out.println("Jogador " + id + " não encontrado."); //se nao achar, mensagem de erro
        }
    }

    public int getNumeroJogadores() { return numJogadores; }//retorna quantidade de jogadores

    public void gravarArquivo() { //cria um arquivo
        File arquivo = new File("Arquivo.dat");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(jogadores);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("Dados gravados com sucesso " + arquivo.getName());
        } catch (Exception e) {
            System.err.println("Erro ao gravar arquivo: " + e);
        }
    }


    public void lerArquivo() { //le um arquivo
        File arquivo = new File("Arquivo.dat");
        try {
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            jogadores = (Jogador[]) objectInputStream.readObject();
            numJogadores = jogadores.length; // Atualiza o número de jogadores
            objectInputStream.close();
            fileInputStream.close();

            System.out.println("Dados lidos com sucesso " + arquivo.getName());
        } catch (Exception e) {
            System.err.println("Erro ao ler arquivo: " + e);
        }
    }

    public void iniciarTorneio() {
        int rodadas = 1;
        for(int i = 0; i< numJogadores; i++) jogadores[i].setSaldo(100); //inicia o saldo para todos os jogadores em 100 creditos

        boolean fimTorneio = false;
        boolean temGanhador = false;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Escolha um jogo");// menu interativo
        System.out.println("1 - Jogo de Azar;");
        System.out.println("2 - Jogo do Porquinho;");
        int op = entrada.nextInt();
        entrada.nextLine();

        while(!fimTorneio){
            System.out.println("Rodada " + rodadas++);

            switch (op) {
                case 1:

                    while(!temGanhador) {
                        int valor = 0;
                        int numGanhadores = 0; //ganhadores do craps
                        int[] ganhadores = new int[numJogadores]; //vetor de ganhadores, pode ter mais de um
                        int indexGanhadores = 0; // indice no vetor

                        pedirAposta(entrada); //funcao para pedir a aposta dos jogadores

                        for(int i = 0; i< numJogadores; i++){
                            if(jogadores[i].getSaldo()>0){ //verifica se tem saldo positivo
                                jogadores[i].setJogo(2); //adiciona dois dados no jogo
                                jogadores[i].rolarDados(); //rola os dados

                                if(jogadores[i].jogoCraps()==1){ //craps retorna 1 caso o jogador ganhe e -1 se ele perde
                                    System.out.println(jogadores[i].getId() + " ganhou");

                                    ganhadores[indexGanhadores++] = i; //adiciona a posicao do jogador ganhador em um vetor de ganhadores
                                    numGanhadores++; //incrementa o numero de ganhadores
                                    temGanhador = true;
                                } else {
                                    System.out.println(jogadores[i].getId() + " perdeu");
                                    System.out.println();
                                }
                            }
                        }

                        int maiorAposta = 0;
                        if(numGanhadores>0){
                            int numGanhadoresMaiorAposta=0;

                            for(int i=0; i<numGanhadores; i++) {
                                int jogadorIndex = ganhadores[i];
                                int aposta = jogadores[jogadorIndex].getAposta();

                                if(aposta>maiorAposta) {
                                    maiorAposta = aposta;
                                    numGanhadoresMaiorAposta = 1;
                                }else numGanhadoresMaiorAposta++;
                            }

                            valor = getValor(valor, numGanhadores, ganhadores, maiorAposta);

                            if(numGanhadoresMaiorAposta>0) {
                                int premio = valor / numGanhadoresMaiorAposta;
                                for (int i=0; i<numGanhadores; i++) {
                                    int jogadorIndex = ganhadores[i];
                                    if (jogadores[jogadorIndex].getAposta() == maiorAposta) jogadores[jogadorIndex].setSaldo(premio);
                                }
                            }

                        }else System.out.println("Todos os jogadores perderam :(");

                        System.out.println("Fim da Rodada");
                        for(int i = 0; i< numJogadores; i++) { //no final de rodada, imprime o jogador ganhador e o saldo de cada jogador
                            for (int k=0; k<numGanhadores; k++) {
                                if (ganhadores[k]==i && jogadores[i].getAposta()==maiorAposta) {
                                    System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo() + " (Ganhador da Rodada)");
                                    break;
                                }
                                else System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                            }
                        }
                        break;
                    }
                    break;

                case 2:
                    temGanhador = false;

                    while(!temGanhador) {
                        int valor = 0;
                        int[] tentativas = new int[numJogadores]; //vetor para guardar o numero de tentativas de cada jogador

                        pedirAposta(entrada); //pede a aposta de cada jogador humano

                        for(int i = 0; i< numJogadores; i++){ //jogadores rolam os dados
                            if(jogadores[i].getSaldo()>0){//verifica se o jogador tem saldo
                                jogadores[i].setJogo(2);
                                System.out.println("Jogador " + jogadores[i].getId() + " rolando os dados:");

                                tentativas[i] = jogadores[i].jogoDoble(); //guarda o numero de tentativas do jogador na posicao correspondente

                                System.out.println("Valor Atingido");
                                System.out.println();
                            }else
                                jogadores[i].setAposta(0);
                        }
                        int menor = Integer.MAX_VALUE;

                        for(int i = 0; i< numJogadores; i++) if(tentativas[i]<menor && tentativas[i]!=0) menor = tentativas[i]; //acha o menor numero de tentativas entre todos os jogadores

                        int numGanhadores = 0;
                        int[] ganhadores = new int[numJogadores];

                        for(int i = 0; i< numJogadores; i++) if(tentativas[i]==menor) ganhadores[numGanhadores++] = i;  //conta quantos jogadores possuem o menor numero de tentativas e guarda a posicao dos ganhadores em um vetor

                        int maiorAposta = getMaiorAposta(numGanhadores, ganhadores);

                        valor = getValor(valor, numGanhadores, ganhadores, maiorAposta);

                        if(numGanhadores>0) {
                            int premio = valor/numGanhadores; //distribui o premio entre os ganhadores
                            for(int i=0; i<numGanhadores; i++) if(jogadores[ganhadores[i]].getAposta()==maiorAposta) jogadores[ganhadores[i]].setSaldo(premio);
                        }
                        temGanhador = true;

                        System.out.println("*Fim da Rodada*");

                        for (int i = 0; i< numJogadores; i++) { //cada fim de rodada, imprime o saldo e o numero de tentativas dos jogadores ativos
                            if(tentativas[i]!=0){
                                System.out.println("Total de tentativas de " + jogadores[i].getId() + ": " + tentativas[i]);
                                System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                            }
                        }

                        for (int i = 0; i< numJogadores; i++) for(int k=0; k<numGanhadores; k++) if(i==ganhadores[k] && jogadores[ganhadores[k]].getAposta()==maiorAposta) System.out.println("-->Ganhador da rodada: " + jogadores[i].getId());
                        break;
                    }
                    break;
            }

            int jogadoresAtivos = 0;
            for(int i = 0; i< numJogadores; i++) if(jogadores[i].getSaldo()>0) jogadoresAtivos++; //verifica quantos jogadores ativos ainda restam no torneio

            if(jogadoresAtivos==1){
                fimTorneio = true; //fim do Torneio com apenas 1 jogador restante
                rodadas = rodadas-1;

                System.out.println("Resultado Final"); //resultado final do torneio
                System.out.println("Rodadas jogadas: " + rodadas); //imprime a quantidade de rodadas totais
                for (int i = 0; i < numJogadores; i++) System.out.println(jogadores[i].getSaldo() > 0 ? "Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo() + "(Ganhador)" : "Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
            }
        }
    }

    public void pedirAposta(Scanner sc) { // pede a aposta dos jogadores humanos
        for(int i = 0; i< numJogadores; i++){
            if(jogadores[i].getSaldo()>0) {
                if (jogadores[i].getHumano()){
                    System.out.printf("valor da aposta de " + jogadores[i].getId() + ": ");
                    int aposta = sc.nextInt();
                    sc.nextLine();
                    jogadores[i].setAposta(aposta);
                }
            }
        }
    }

    public int getValor(int valor, int numGanhadores, int[] ganhadores, int maiorAposta) {
        for(int i = 0; i< numJogadores; i++) {
            boolean isGanhador = false;

            for(int k=0; k<numGanhadores; k++) {
                if(ganhadores[k]==i && jogadores[i].getAposta()==maiorAposta) {
                    isGanhador = true;
                    break;
                }
            }
            if(!isGanhador && jogadores[i].getSaldo()>0) {
                jogadores[i].atualizaSaldo();
                valor += jogadores[i].getAposta();
            }
        }
        return valor;
    }

    private int getMaiorAposta(int numGanhadores, int[] ganhadores) {
        int maiorAposta = 0;

        for(int i = 0; i< numGanhadores; i++){
            int jogadorIndex = ganhadores[i];
            int aposta = jogadores[jogadorIndex].getAposta();

            if(aposta>maiorAposta)
                maiorAposta = aposta;
            else
                break;
        }
        return maiorAposta;
    }
}
