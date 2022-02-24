import java.util.Random;
import java.lang.Math;

class TP01Q04{
    private static Random gerador = new Random();

    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Altera os caracteres da string "s" com a constante do gerador (4)
    public static String alteracaoAleatorio(String s){
        String resp = "";
        char char_s, char_resp;

        char_s = (char)('a' + Math.abs(gerador.nextInt()) % 26);
        char_resp = (char)('a' + Math.abs(gerador.nextInt()) % 26);

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == char_s){
                resp += (char)(char_resp);
            }else{
                resp += (char)(s.charAt(i));
            }
        }

        return resp;
    }

    //Main (reciclagem)
    public static void main(String[] args){
        //Seed = 4
        gerador.setSeed(4);
        String[] entrada = new String[1000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(alteracaoAleatorio(entrada[i]));
        }
    }
}
