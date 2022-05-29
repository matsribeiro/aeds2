import java.util.*;

public class pesquisa_sequencial {

    //Checa se o input deve ser terminado
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Acha o nome da serie a partir do nome de seu arquivo
    public static String limpaNome(String nomeArquivo){
        String nomeFilme = "";

        for(int i = 0; nomeArquivo.charAt(i) != '.'; i++){
            if(nomeArquivo.charAt(i) == '_'){
                nomeFilme += " ";
            } else {
                nomeFilme += nomeArquivo.charAt(i);
            }
        }

        return nomeFilme;
    }

    public static String padronizaNome(String nomeArquivo){
        String nomeFilme = "";

        for(int i = 0; i < nomeArquivo.length(); i++){
            if(nomeArquivo.charAt(i) == 'Ú'){
                nomeFilme += "U";
            } else if(nomeArquivo.charAt(i) == 'á') {
                nomeFilme += "a";
            } else if(nomeArquivo.charAt(i) == ':') {
                nomeFilme += " -";
            } else if(nomeArquivo.charAt(i) == 'é') {
                nomeFilme += "e";
            } else {
                nomeFilme += nomeArquivo.charAt(i);
            }
        }
        return nomeFilme;
    }

    public static int pesquisaSequencial(String s, String[] array){
        for(int i = 0; i < array.length; i++){
            if(s.equals(array[i]))
                return i;
        }

        return -1;
    }

    public static void main(String[] args){
        MyIO.setCharset("UTF-8");
        String[] baseFilmes = new String[1000];
        String linha_nova;
        String nome;
        int num_linha = 0, parar = 0;
        
        do{
            linha_nova = MyIO.readLine();

            if(isFim(linha_nova) == true)
                parar = 1;
            else
                baseFilmes[num_linha++] = limpaNome(linha_nova);
        }while(parar != 1);

        baseFilmes[num_linha] = null;
        parar = 0;
       
        do{
            linha_nova = MyIO.readLine();

            if(isFim(linha_nova) == true){
                parar = 1;
            }else{
                nome = padronizaNome(linha_nova);
                if(pesquisaSequencial(nome, baseFilmes) != -1){
                    System.out.println("SIM");
                }else{
                    System.out.println("NAO");
                }
            }
        }while(parar != 1);
    }
}