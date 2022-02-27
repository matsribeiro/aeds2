public class TP01Q06{

    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Checa vogais
    public static String Vogal(String s){

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u'){
                return "NAO ";
            }
        }

        return "SIM ";
    }

    //Checa consoantes
    public static String Consoante(String s){

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || !((s.charAt(i) >= 97 && s.charAt(i) <= 122))) {
                return "NAO ";
            }
        }

        return "SIM ";
    }

    //Checa inteiros
    public static String Inteiro(String s){

        for(int i = 0; i < s.length(); i++){
            if(!((s.charAt(i) >= '0' && s.charAt(i) <= '9'))){
                return "NAO ";
            }
        }

        return "SIM ";
    }

    //Checa reais
    public static String Real(String s){
        int virgula_ponto = 0;

        for(int i = 0; i < s.length(); i++){
            if((s.charAt(i) >= '0' && s.charAt(i) <= '9') == false){
                if((s.charAt(i) == ',' || s.charAt(i) == '.' || s.charAt(i) == ';') && virgula_ponto == 0)
                    virgula_ponto++;
                else
                    return "NAO";
            }
        }

        return "SIM";
    }

    //Main (reciclagem)
        public static void main(String[] args){
        MyIO.setCharset("UTF-8");
        String[] entrada = new String[2000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(Vogal(entrada[i]) + Consoante(entrada[i]) + Inteiro(entrada[i]) + Real(entrada[i]));
        }
    }
}
