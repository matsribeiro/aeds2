class TP01Q05 {
    //FIM (reciclagem)
    public static boolean isFim(String s) {
        return (s.length() == 1 && s.charAt(0) == '0');
    }

    //Metodo and
    public static boolean And(String linha, int i) {
        if (linha.charAt(i) == 'a') {
            return true;
        }
        return false;
    }

    //Metodo not
    public static boolean Not(String linha, int i) {
        if (linha.charAt(i) == 'n') {
            return true;
        }
        return false;
    }

    //Metodo or
    public static boolean Or(String linha, int i) {
        if (linha.charAt(i) == 'o') {
            return true;
        }
        return false;
    }

    //Metodo para transformar int em bool - 0 = false / 1 = true
    public static boolean TransformarBoolean(int valor) {
        if (valor == 0) {
            return false;
        } else {
            return true;
        }
    }

    //Algebra
    public static boolean Algebra(String linha, int quantidade) {
        int[] array = new int[quantidade];
        int j = 2;
        boolean bool = false;

        for (int i = 0; i < quantidade; i = i + 1) {
            array[i] = ((int) linha.charAt(j)) - 48;
            j = j + 2;
        }
        
        //2 variaveis
        if (quantidade == 2) {
            if (And(linha, j)) {
                //and
                if (Not(linha, j + 4)) {
                    //not
                    if (Not(linha, j + 13)) {
                        //not
                        bool = (! TransformarBoolean(array[0])) && (! TransformarBoolean(array[1]));
                        return bool;
                    }
                }
            }
            
            if (Not(linha, j)) {
                //not
                if (And(linha, j + 4)) {
                    //and
                    bool = ! (TransformarBoolean(array[0]) && TransformarBoolean(array[1]));
                    return bool;
                }
            }
        }

        //3 variaveis
        if (quantidade == 3) {
            if (And(linha, j)) {
                //and
                if (Or(linha, j + 4)) {
                    //or
                    if (Not(linha, j + 16)) {
                        //not
                        if (And(linha, j + 20)) {
                            //and
                            bool = ((TransformarBoolean(array[0]) || TransformarBoolean(array[1])) && (! (TransformarBoolean(array[1]) && TransformarBoolean(array[2]))));
                            return bool;
                        }
                    }

                    if (linha.charAt(j + 7) == 'A') {
                        bool = (TransformarBoolean(array[0]) || TransformarBoolean(array[1]) || (! (TransformarBoolean(array[2])))) && (TransformarBoolean(array[1]) || TransformarBoolean(array[2])) && (! TransformarBoolean(array[0]) || TransformarBoolean(array[2]));
                        return bool;
                    }
                }

                if (linha.charAt(j + 4) == 'A'){
                    if (Or(linha, j + 8)) {
                        //or
                        bool = (TransformarBoolean(array[0]) && (TransformarBoolean(array[1]) || TransformarBoolean(array[2])));
                        return bool;
                    }
                }


            }

            if (Or(linha, j)) {
                //or
                if (And(linha, j + 3)) {
                    //and
                    if (And(linha, j + 16)) {
                        //and
                        bool = ((TransformarBoolean(array[0]) && TransformarBoolean(array[1])) || (TransformarBoolean(array[0]) && TransformarBoolean(array[2])));
                        return bool;
                    }

                    if (And(linha, j + 15)) {
                        //and
                        bool = ((TransformarBoolean(array[0]) && TransformarBoolean(array[1])) || (TransformarBoolean(array[0]) && TransformarBoolean(array[2])));
                        return bool;
                    }

                    if (And(linha, j + 20)) {
                        //and
                        if (Or(linha, j + 24)) {
                            //or
                            bool = (TransformarBoolean(array[0]) && TransformarBoolean(array[1]) && TransformarBoolean(array[2])) || ((TransformarBoolean(array[0]) || TransformarBoolean(array[1])) && TransformarBoolean(array[2]));
                            return bool;
                        }
                        if (linha.charAt(j + 24) == 'A') {
                            if (Not(linha, j + 28)) {
                                //not
                                if (linha.charAt(j + 37) == 'C') {
                                    bool = (TransformarBoolean(array[0]) && TransformarBoolean(array[1]) && TransformarBoolean(array[2])) || (TransformarBoolean(array[0]) && (! (TransformarBoolean(array[1]))) && TransformarBoolean(array[2])) || ((!(TransformarBoolean(array[0]))) && (!(TransformarBoolean(array[1]))) && TransformarBoolean(array[2])) || ((!(TransformarBoolean(array[0]))) && (!(TransformarBoolean(array[1]))) && (!(TransformarBoolean(array[2]))));
                                    return bool;
                                }
                            }
                        }
                        if (Not(linha, j + 37)) {
                            //not
                            bool = ((TransformarBoolean(array[0]) && TransformarBoolean(array[1]) && TransformarBoolean(array[2]))) || ((TransformarBoolean(array[0]) && (! (TransformarBoolean(array[1]))) && (! (! (TransformarBoolean(array[0])) && (! (TransformarBoolean(array[2])))))));
                            return bool;
                        }
                    }
                }

                if (linha.charAt(j + 3) == 'A') {
                    if (And(linha, j + 7)) {
                        //and
                        bool = (TransformarBoolean(array[0]) || (TransformarBoolean(array[1]) && TransformarBoolean(array[2])));
                        return bool;
                    }
                }

                if (Or(linha, j + 3)) {
                    //or
                    if (And(linha, j + 6)) {
                        //and
                        bool = (((! (TransformarBoolean(array[0]) && TransformarBoolean(array[1]))) && (! (TransformarBoolean(array[2])))) || ((! (TransformarBoolean(array[0]))) && TransformarBoolean(array[1]) && TransformarBoolean(array[2])) || (TransformarBoolean(array[0]) && TransformarBoolean(array[1]) && TransformarBoolean(array[2])) || (TransformarBoolean(array[0]) && (! (TransformarBoolean(array[1]))) && (! (TransformarBoolean(array[2]))))) || (TransformarBoolean(array[0]) && (! (TransformarBoolean(array[1]))) && TransformarBoolean(array[2]));
                        return bool;
                    }
                    bool =  (TransformarBoolean(array[0]) || TransformarBoolean(array[1])) || TransformarBoolean(array[2]);
                    return bool;
                }

                if (Not(linha, j + 3)) {
                    //not
                    bool = !TransformarBoolean(array[0]) || TransformarBoolean(array[1]) || (TransformarBoolean(array[0]) && TransformarBoolean(array[1]) && (! TransformarBoolean(array[2])));
                    return bool;
                }
            }
        }
        return false;
    }

    //Main (+/- reciclagem)
    public static void main(String[] args) {
        MyIO.setCharset("ISO-8859-1");
        String[] entrada = new String[1000];
        int numEntrada = 0;
        int quantidade = 0;
        boolean resp = false;

        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;

        for (int i = 0; i < numEntrada; i = i + 1) {
            quantidade = ((int) entrada[i].charAt(0)) - 48;
            resp = Algebra(entrada[i], quantidade);
            if (resp == true) {
                MyIO.println(1);
            } else {
                MyIO.println(0);
            }
        }
    }
}