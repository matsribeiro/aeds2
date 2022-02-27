public class TP01Q15{

    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Checa vogais
    public static String Vogal(String s, int i){

        if(i < s.length()){
            if(s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u'){
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return Vogal(s, i+1);
    }
    
    //Chama a metodo 'vogais'
    public static String Vogal(String s){
        return Vogal(s, 0);
    }
    
    //Checa consoantes
    public static String Consoante(String s, int i){

        if(i < s.length()){
            if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || !((s.charAt(i) >= 97 && s.charAt(i) <= 122))) {
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return Consoante(s, i+1);
    }

    //Chama a metodo 'consoante'
    public static String Consoante(String s){
        return Consoante(s, 0);
    }

    //Checa inteiros
    public static String Inteiro(String s, int i){

        if(i < s.length()){
            if(!((s.charAt(i) >= '0' && s.charAt(i) <= '9'))){
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return Inteiro(s, i+1);
    }

    //Chama a metodo 'inteiro'
    public static String Inteiro(String s){
        return Inteiro(s, 0);
    }

    //Checa reais
     public static String Real(String s, int i, int virgula_ponto){

        if(i < s.length()){
            if((s.charAt(i) >= '0' && s.charAt(i) <= '9') == false){
                if((s.charAt(i) == ',' || s.charAt(i) == '.') && virgula_ponto == 0)
                    virgula_ponto++;
                else
                    return "NAO";
            }
        }else{
            return "SIM";
        }

        return Real(s, i+1, virgula_ponto);
    }

    //Chama a metodo 'real'
    public static String Real(String s){
        return Real(s, 0, 0);
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
