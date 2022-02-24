class TP01Q03{
    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Altera os caracteres da string "s" com a constante da cifra
    public static String ciframento(String s){
        String resp = "";
        int cifra = 3;

        for(int i = 0; i < s.length(); i++){
            resp += (char)(s.charAt(i) + cifra);
        }

        return resp;
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
            MyIO.println(ciframento(entrada[i]));
        }
    }
}
