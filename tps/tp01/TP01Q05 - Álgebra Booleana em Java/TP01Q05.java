class TP01Q05{

    //Variaveis globais
    static int A, B, C, index;
    static String statement;

    public static int typeCast(char c){
        if(c == '0')
            return 0;
        else if(c == '3')
            return 3;
        
        return 1;
    }

    //Inicializa as variaveis de entrada
    public static int novaAlgebra(String s){
        statement = s;

        //A e B
        A = typeCast(statement.charAt(2));
        B = typeCast(statement.charAt(4));
        index = 6;

        //C (nem sempre utilizada)
        if(typeCast(statement.charAt(0)) == 3){
            C = typeCast(statement.charAt(6));
            index = 8;
        }

        return algebra();
    }

    public static int and(){
        int end = 0;
        int resp_parcial, resp_final = 1;

        while(end != -1){
            resp_parcial = algebra();

            if(resp_parcial == 0){
                resp_final = 0;
            }else if(resp_parcial == -1){
                end = -1;
            }
        }

        return resp_final; 
    }

    public static int or(){
        int end = 0;
        int resp_parcial, resp_final = 0;

        while(end != -1){
            resp_parcial = algebra();
            
            if(resp_parcial == 1){
                resp_final =  1;
            }else if(resp_parcial == -1){
                end = -1;
            }
        }

        return resp_final;
    }

    public static int not(){
        int resp = algebra();
        index++;

        if(resp == 1){
            return 0;
        }

        return 1;
    }
    
    public static int algebra(){
        
        //Processa a algebra
        for( ; index < statement.length(); index++){
            switch(statement.charAt(index)){
                case 'a': //and
                    index += 4;
                    return and();
                case 'o': //or
                    index += 3;
                    return or();
                case 'n': //not
                    index += 4;
                    return not();
                case 'A':
                    index++;
                    return A;
                case 'B':
                    index++;
                    return B;
                case 'C':
                    index++;
                    return C;
                case ')':
                    index++;
                    return -1;
                default:
                    break;
            }
        }

        return 0;
    }

    public static boolean isFim(String s){
        return (s.length() == 1 && s.startsWith("0"));
    }

    public static void main(String[] args){
        String[] entrada = new String[2000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            MyIO.println(novaAlgebra(entrada[i]));
        }
    }
}
