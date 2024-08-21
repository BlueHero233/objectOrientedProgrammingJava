import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Torneio {
    private Jogador[] jogadores;
    private int numJogadores;
    private static final int capacidadeMax = 10;


    public Torneio() { //inicializa o torneio com 0 jogadores e capacidade 10
        this.jogadores = new Jogador[capacidadeMax];
        this.numJogadores = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) { //verifica se o limite ja nao foi alcançado
            jogadores[numJogadores++] = jogador; //incrementa o numero de jogadores
            System.out.println("Jogador " + jogador.getId() + " adicionado.");
        } else {
            System.out.println("Não é possível adicionar mais jogadores.");
        }
    }

    public void removerJogador(String id) {
        for (int i = 0; i< numJogadores; i++) {
            if (jogadores[i].getId().equals(id)) {
                jogadores[i] = jogadores[numJogadores -1]; //passa o ultimo jogador para a posicao do jogador removido
                jogadores[numJogadores -1] = null;
                numJogadores--; //diminui a quantidade de jogadores
                System.out.println("Jogador " + id + " removido com sucesso.");
                break;
            }
            else System.out.println("Jogador " + id + " não encontrado."); //caso o id do jogador que se deseja remover nao seja encontrado
        }
    }

    public int getNumeroJogadores(){
        return this.numJogadores;
    }

    public void gravarArquivo() {
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


    public void lerArquivo() {
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
        for(int i = 0; i< numJogadores; i++) jogadores[i].setSaldo(100); //set o saldo para o valor original(100)

        boolean fimTorneio = false;
        Scanner sc = new Scanner(System.in);

        //usuario escolhe o jogo a cada inicio de torneio
        System.out.println("Escolha um jogo");
        System.out.println("1 - Jogo de Azar;");
        System.out.println("2 - Jogo do Porquinho;");
        System.out.print("Opção: ");
        int op = sc.nextInt();
        sc.nextLine();

        while(!fimTorneio){
            System.out.println("Rodada " + rodadas++);

            switch (op) {
                case 1:
                    boolean temGanhador = false;

                    while(!temGanhador) {
                        int valor = 0;
                        int numGanhadores = 0;
                        int[] ganhadores = new int[numJogadores];
                        int indexGanhadores = 0;

                        //loop para pedir a aposta dos jogadores
                        for(int i = 0; i< numJogadores; i++){
                            if(jogadores[i].getSaldo()>0) { //verifica se o jogador ja nao foi desqualificado
                                if (jogadores[i].getHumano()==true){ //ira pedir a aposta apenas dos jogadores humanos
                                    System.out.printf("valor da aposta de " + jogadores[i].getId() + ": ");
                                    int aposta = sc.nextInt();
                                    sc.nextLine();
                                    jogadores[i].setAposta(aposta);
                                }
                            }
                        }

                        //jogadores rolam os dados
                        for(int i = 0; i< numJogadores; i++){
                            if(jogadores[i].getSaldo()>0){
                                jogadores[i].setJogo(2);
                                jogadores[i].rolarDados();

                                if(jogadores[i].jogoCraps()==1){ //jogoAzar retorna 1 caso o jogador ganhe
                                    System.out.println(jogadores[i].getId() + " ganhou");
                                    System.out.println();

                                    ganhadores[indexGanhadores++] = i; //adiciona a posicao do jogador ganhador em um vetor de ganhadores
                                    numGanhadores++; //incrementa o numero de ganhadores
                                    temGanhador = true; //true caso haja ganhador
                                } else { //caso o jogador perca
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
                                    numGanhadoresMaiorAposta = 1; // Resetar contagem para novo maior valor
                                }else if(aposta==maiorAposta)
                                    numGanhadoresMaiorAposta++; // Contar quantos têm a maior aposta
                            }

                            for(int i = 0; i< numJogadores; i++) {
                                boolean ehGanhadorComMaiorAposta = false;
                                for (int k=0; k<numGanhadores; k++) {
                                    if (ganhadores[k]==i && jogadores[i].getAposta()==maiorAposta) {
                                        ehGanhadorComMaiorAposta = true;
                                        break;
                                    }
                                }
                                if(!ehGanhadorComMaiorAposta && jogadores[i].getSaldo()>0){
                                    jogadores[i].atualizaSaldo();
                                    valor += jogadores[i].getAposta();
                                }
                            }

                            if(numGanhadoresMaiorAposta>0) {
                                int premio = valor / numGanhadoresMaiorAposta;
                                for (int i=0; i<numGanhadores; i++) {
                                    int jogadorIndex = ganhadores[i];
                                    if (jogadores[jogadorIndex].getAposta() == maiorAposta) {
                                        jogadores[jogadorIndex].setSaldo(premio);
                                    }
                                }
                            }

                        }else
                            System.out.println("Todos os jogadores perderam :(");

                        System.out.println("Fim da Rodada");
                        //cada final de rodada, imprime o jogador ganhador e o saldo de cada jogador
                        for(int i = 0; i< numJogadores; i++) {
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

                        //pede o valor da aposta de cada jogador humano
                        for(int i = 0; i< numJogadores; i++){
                            if(jogadores[i].getSaldo()>0){ //verifica se o jogador ja nao foi desqualificado
                                if (jogadores[i].getHumano()){
                                    System.out.printf("valor da aposta de " + jogadores[i].getId() + ": ");
                                    int aposta = sc.nextInt();
                                    sc.nextLine();
                                    jogadores[i].setAposta(aposta);
                                }
                            }
                        }

                        //jogadores rolam os dados
                        for(int i = 0; i< numJogadores; i++){
                            if(jogadores[i].getSaldo()>0){//verifica se o jogador ja nao foi desqualificado
                                jogadores[i].setJogo(2);
                                System.out.println("Jogador " + jogadores[i].getId() + " rolando os dados:");

                                tentativas[i] = jogadores[i].jogoDoble(); //guarda o numero de tentativas do jogador na posicao correspondente

                                System.out.println("Valor Atingido");
                                System.out.println();
                            }else
                                jogadores[i].setAposta(0);
                        }

                        int menor = Integer.MAX_VALUE;
                        //acha o menor numero de tentativas entre todos os jogadores
                        for(int i = 0; i< numJogadores; i++) {
                            if(tentativas[i]<menor && tentativas[i]!=0) {
                                menor = tentativas[i];
                            }
                        }

                        int numGanhadores = 0;
                        int[] ganhadores = new int[numJogadores];

                        //loop conta quantos jogadores possuem o menor numero de tentativas
                        for(int i = 0; i< numJogadores; i++) {
                            if(tentativas[i]==menor) {
                                ganhadores[numGanhadores++] = i; //guarda a posicao dos ganhadores em um vetor
                            }
                        }

                        int maiorAposta = getMaiorAposta(numGanhadores, ganhadores);

                        for(int i = 0; i< numJogadores; i++) {
                            boolean isGanhador = false;

                            //verifica se o jogador eh ganhador
                            for(int k=0; k<numGanhadores; k++) {
                                if(ganhadores[k]==i && jogadores[i].getAposta()==maiorAposta) {
                                    isGanhador = true;
                                    break;
                                }
                            }

                            //caso o jogador nao seja ganhador
                            if(!isGanhador && jogadores[i].getSaldo()>0) {
                                jogadores[i].atualizaSaldo(); //atualiza o Saldo(desconta o valor apostado)
                                valor += jogadores[i].getAposta(); //soma a aposta no valor da rodada
                            }
                        }

                        if(numGanhadores>0) {
                            int premio = valor/numGanhadores; //distribui o premio igualmente entre os ganhadores
                            for(int i=0; i<numGanhadores; i++) {
                                if(jogadores[ganhadores[i]].getAposta()==maiorAposta)
                                    jogadores[ganhadores[i]].setSaldo(premio);
                            }
                        }
                        temGanhador = true;

                        System.out.println("*Fim da Rodada*");
                        //cada fim de rodada, imprime o saldo e o numero de tentativas dos jogadores ativos
                        for (int i = 0; i< numJogadores; i++) {
                            if(tentativas[i]!=0){
                                System.out.println("Total de tentativas de " + jogadores[i].getId() + ": " + tentativas[i]);
                                System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                            }
                        }

                        for (int i = 0; i< numJogadores; i++)
                            for(int k=0; k<numGanhadores; k++)
                                if(i==ganhadores[k] && jogadores[ganhadores[k]].getAposta()==maiorAposta)
                                    System.out.println("-->Ganhador da rodada: " + jogadores[i].getId());
                        break;
                    }
                    break;
            }

            //verifica quantos jogadores ativos ainda restam no torneio
            int jogadoresAtivos = 0;
            for(int i = 0; i< numJogadores; i++) {
                if(jogadores[i].getSaldo()>0){
                    jogadoresAtivos++;
                }
            }

            //caso reste apenas um jogador com saldo positivo
            if(jogadoresAtivos==1){
                fimTorneio = true; //fim do Torneio com apenas 1 jogador restante
                rodadas = rodadas-1;

                //imprime o resultado final do torneio
                System.out.println();
                System.out.println("*RESULTADO FINAL*");
                System.out.println("Rodadas jogadas: " + rodadas); //imprime a quantidade de rodadas do torneio
                for (int i = 0; i < numJogadores; i++) {
                    if(jogadores[i].getSaldo()>0)
                        System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo() + "(Ganhador)");
                    else
                        System.out.println("Saldo de " + jogadores[i].getId() + ": " + jogadores[i].getSaldo());
                }
            }
        }
    }

    private int getMaiorAposta(int numGanhadores, int[] ganhadores) {
        int maiorBet = 0;
        int numGanhadoresMaiorBet=0;

        for(int i = 0; i< numGanhadores; i++){
            int jogadorIndex = ganhadores[i];
            int aposta = jogadores[jogadorIndex].getAposta();

            if(aposta>maiorBet) {
                maiorBet = aposta;
                numGanhadoresMaiorBet=1;
            }else if(maiorBet==aposta)
                numGanhadoresMaiorBet++;
        }
        return maiorBet;
    }
}
