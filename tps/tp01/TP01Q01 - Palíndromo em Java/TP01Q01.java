class TP01Q01 {
   public static boolean isMaiuscula (char c){
      return (c >= 'A' && c <= 'Z');
   }

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static boolean palindromo (String s){
   	String sinvertida = "";	
   	int i;
   	boolean resp;
   	sinvertida = new StringBuffer(s).reverse().toString();
   	if(s.equals(sinvertida)){
   		resp = true;
   	} else {
   		resp = false;
   	}
   	
   	return resp;
   }

   public static void main (String[] args){
      String[] entrada = new String[1000];
      int numEntrada = 0;

      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (isFim(entrada[numEntrada++]) == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i++){
         boolean x = palindromo(entrada[i]);
         if (x == true){
         	System.out.println("SIM");
         } else {
         	System.out.println("NAO");
         }
      }
   }
}
