class TP01Q11{
    
    //Checa se a string fornecida eh um palindromo, *recursivamente*
    public static String palindromo(String s, int i){
        
        //Compara os caracteres
        if(i < s.length()-i-1){
            if(s.charAt(i) != s.charAt(s.length()-i-1)){
                return "NAO";
            }
        }else{
            return "SIM";
        }

        return palindromo(s, i+1);
    }

    //Chama a metodo 'palindromo'
    public static String palindromo(String s){
        return palindromo(s, 0);
    }

    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Main (reciclagem)
    public static void main(String[] args){
        String[] entrada = new String[1000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(palindromo(entrada[i]));
        }
    }
}
