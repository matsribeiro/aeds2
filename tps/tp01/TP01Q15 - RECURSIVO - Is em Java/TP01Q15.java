public class TP01Q15{

    //Checa vogais
    public static String isVogal(String s, int i){

        if(i < s.length()){
            if(s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u'){
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return isVogal(s, i+1);
    }
    
    public static String isVogal(String s){
        return isVogal(s, 0);
    }
    
    public static String isConsoante(String s, int i){

        if(i < s.length()){
            if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || !((s.charAt(i) >= 97 && s.charAt(i) <= 122))) {
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return isConsoante(s, i+1);
    }

    public static String isConsoante(String s){
        return isConsoante(s, 0);
    }

    //Checa inteiros
    public static String isInteiro(String s, int i){

        if(i < s.length()){
            //(ASCII) 0 = 48 ... 9 = 57
            if(!((s.charAt(i) >= 48 && s.charAt(i) <= 57))){
                return "NAO ";
            }
        }else{
            return "SIM ";
        }

        return isInteiro(s, i+1);
    }

    public static String isInteiro(String s){
        return isInteiro(s, 0);
    }

    //Checa reais
     public static String isReal(String s, int i, int virgula_ponto){

        if(i < s.length()){
            if((s.charAt(i) >= 48 && s.charAt(i) <= 57) == false){
                if((s.charAt(i) == ',' || s.charAt(i) == '.') && virgula_ponto == 0)
                    virgula_ponto++;
                else
                    return "NAO";
            }
        }else{
            return "SIM";
        }

        return isReal(s, i+1, virgula_ponto);
    }

    public static String isReal(String s){
        return isReal(s, 0, 0);
    }

    //FIM
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args){
        MyIO.setCharset("UTF-8");
        String[] entrada = new String[2000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(isVogal(entrada[i]) + isConsoante(entrada[i]) + isInteiro(entrada[i]) + isReal(entrada[i]));
        }
    }
}
