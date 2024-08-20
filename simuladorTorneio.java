import java.util.Scanner;

public class simuladorTorneio{
    public static void main( String[] args ){
        Scanner entrada = new Scanner( System.in );
        Torneio torneio = new Torneio();
        boolean continuar = true;
        String nomeArquivo;
        while ( continuar ){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Adicionar jogador");
            System.out.println("2 - Remover jogador");
            System.out.println("3 - Iniciar torneio");
            System.out.println("4 - Gravar os dados do torneio em arquivo");
            System.out.println("5 - Ler os dados do torneio em arquivo");
            System.out.println("6 - Sair do programa");
            int opcao = entrada.nextInt();
            switch ( opcao ){
                case 1:
                    if(torneio.getNumJogadores()>=10) {
                        System.out.println("Limite de 10 jogadores atingido. Não é possível adicionar mais jogadores.");
                    }else {
                        System.out.println("id do jogador: ");
                        String id = entrada.nextLine();

                        System.out.println("humano: (true/false)");
                        boolean humano = entrada.nextBoolean();

                        Jogador novoJogador = new Jogador(id, humano); //cria um novo jogador
                        torneio.adicionarJogador(novoJogador); //adiciona o jogador no torneio
                    }
                    break;
                case 2:
                    if(torneio.getNumJogadores()==0) { //verifica se ha jogadores no torneio
                        System.out.println("Não há jogadores para remover.");
                    }else {
                        System.out.println("id do jogador a ser removido: ");
                        String idRemover = entrada.nextLine();
                        torneio.removerJogador(idRemover);
                    }
                    break;
                case 3:
                    if (torneio.getNumJogadores()>1) { //verifica se a quantidade de pessoas no torneio eh maior que 1
                        torneio.iniciarTorneio();
                    }else {
                        System.out.println("Adicione pelo menos 2 jogadores para iniciar o torneio.");
                    }
                    break;

                case 4:
                    System.out.println("Gravar os dados do torneio: ");
                    nomeArquivo = entrada.nextLine();
                    torneio.gravarArquivo(nomeArquivo);
                    break;

                case 5:
                    System.out.println("Ler os dados do torneio: ");
                    nomeArquivo = entrada.nextLine();
                    torneio.lerArquivo(nomeArquivo);
                    break;

                case 6: // Sair
                    System.out.println("*Program encerrado*");
                    continuar = false; //sai do programa
                    break;

                default:
                    System.out.println("Opção inválida."); // caso nenhuma das opcoes acima seja inserida
            }
        }
        entrada.close();
    }
}