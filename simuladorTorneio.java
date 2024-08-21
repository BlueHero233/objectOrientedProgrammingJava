import java.util.Scanner;

public class simuladorTorneio{
    public static void main( String[] args ){
        Scanner entrada = new Scanner( System.in ); //input do teclado
        Torneio torneio = new Torneio(); //inicializacao do torneio
        boolean continuar = true; //saida do menu
        while ( continuar ){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Adicionar jogador");
            System.out.println("2 - Remover jogador");
            System.out.println("3 - Iniciar torneio");
            System.out.println("4 - Gravar os dados do torneio em arquivo");
            System.out.println("5 - Ler os dados do torneio em arquivo");
            System.out.println("6 - Sair do programa");
            int opcao = entrada.nextInt();
            entrada.nextLine(); //nescessario senao ele pula linha e quebra na entrada boolean
            switch ( opcao ){
                case 1:
                    if(torneio.getNumJogadores()>=10) System.out.println("Limite de 10 jogadores atingido. Não é possível adicionar mais jogadores"); //se tiver 10 jogadores, nao adiciona
                    else {
                        System.out.println("id do jogador: ");
                        String id = entrada.nextLine(); //entrada do nome

                        System.out.println("humano: (true/false)");
                        boolean humano = entrada.nextBoolean(); //booleana se é humano ou nao

                        Jogador novoJogador = new Jogador(id, humano); //inicializa um novo jogador
                        torneio.adicionarJogador(novoJogador); //adiciona o jogador no torneio
                    }
                    break;
                case 2:
                    if(torneio.getNumJogadores()==0) System.out.println("Não há jogadores para remover"); //se nao tiver jogadores, throw exception
                    else {
                        System.out.println("Id do jogador a ser removido: ");
                        String idRemover = entrada.nextLine();
                        torneio.removerJogador(idRemover); //se achar id, remove do vetor de jogadores
                    }
                    break;
                case 3:
                    if (torneio.getNumJogadores()>1) torneio.iniciarTorneio(); //verifica se a quantidade de pessoas no torneio eh maior que 1
                    else System.out.println("Adicione pelo menos 2 jogadores para iniciar o torneio");
                    break;

                case 4:
                    System.out.println("Gravar os dados do torneio: ");
                    torneio.gravarArquivo(); //grava "Arquivo.dat"
                    break;

                case 5:
                    System.out.println("Ler os dados do torneio: ");
                    torneio.lerArquivo(); //le "Arquivo.dat"
                    break;

                case 6:
                    System.out.println("Program encerrado");
                    continuar = false; //sai do programa
                    break;
                default:
                    System.out.println("Opção inválida"); // excepcao
            }
        }
        entrada.close(); //fecha input teclado
    }
}