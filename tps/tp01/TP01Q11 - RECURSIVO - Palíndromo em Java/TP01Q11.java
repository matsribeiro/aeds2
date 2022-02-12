class TP01Q11{
    
    //Checa se a string fornecida eh um palindromo, *recursivamente*
    public static String isPalindromo(String s, int index){
        
        //Compara os caracteres
        if(index < s.length()-index-1){
            if(s.charAt(index) != s.charAt(s.length()-index-1)){
                return "NAO";
            }
        }else{
            return "SIM";
        }

        return isPalindromo(s, index+1);
    }

    public static String isPalindromo(String s){
        return isPalindromo(s, 0);
    }


    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Reciclagem da main de outras questoes
    public static void main(String[] args){
        String[] entrada = new String[1000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(isPalindromo(entrada[i]));
        }
    }
}
