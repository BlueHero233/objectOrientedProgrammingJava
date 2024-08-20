public class JogoDados{
    private Dado[] dados;

    public JogoDados(int qtd){
        dados = new Dado[qtd];
        for(int i=0;i<dados.length; i++)
            dados[i] = new Dado();
    }
    public void rolarDados(){
        for (Dado dado : dados) dado.sortearNumero();
    }
    public int craps(){
        int craps = 0;
        for (Dado item : dados) craps += item.getNumero();
        System.out.println(craps);
        if (craps == 7 || craps == 11)
            return 1;
        else if (craps == 2 || craps == 3 || craps == 12)
            return -1;
        else{
            int point = craps;
            while (true){
                craps = 0;
                for (Dado value : dados) {
                    value.sortearNumero();
                    System.out.println(value.getNumero());
                }
                for (Dado dado : dados)
                    craps += dado.getNumero();
                if (craps == point)
                    return 1;
                else if (craps == 2 || craps == 3 || craps == 12)
                    return -1;
            }
        }
    }
    public int doble(){
        int dobles = 0;
        int tentativas = 0;
        while (dobles<300){
            tentativas++;
            dados[0].sortearNumero();
            dados[1].sortearNumero();
            int dado1 = dados[0].getNumero();
            int dado2 = dados[1].getNumero();
            if (dado1 == dado2){
                if (dado1==1)
                    dobles += 30;
                else
                    dobles += (dado1*dado2)*2;
            }else dobles += (dado1*dado2);
            System.out.println("Pontos atuais: " + dobles);
        }
        return tentativas;
    }
}