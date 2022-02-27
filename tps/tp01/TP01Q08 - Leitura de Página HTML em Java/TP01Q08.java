import java.io.*;
import java.net.*;

class TP01Q08{

    //Ler HTML (reciclagem)
    public static String getHtml(String endereco){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); 
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

        try {
            is.close();
        } catch (IOException ioe) {
            
        }

        return resp;
    }

    public static String readHtml(String html){
        int a = 0, e = 0, i = 0, o = 0, u = 0;
        int a_agudo = 0, e_agudo = 0, i_agudo = 0, o_agudo = 0, u_agudo = 0;
        int a_grave = 0, e_grave = 0, i_grave = 0, o_grave = 0, u_grave = 0;
        int a_til = 0, o_til = 0;
        int a_circunflexo = 0, e_circunflexo = 0, i_circunflexo = 0, o_circunflexo = 0, u_circunflexo = 0;
        int consoantes = 0;
        int br = 0, table = 0;

        for(int j = 0; j < html.length(); j++){
            switch(html.charAt(j)){
                case 'a':
                    a++;
                    break;
                case 'e':
                    e++;
                    break;
                case 'i':
                    i++;
                    break;
                case 'o':
                    o++;
                    break;
                case 'u':
                    u++;
                    break;
                case 'á':
                    a_agudo++;
                    break;
                case 'é':
                    e_agudo++;
                    break;
                case 'í':
                    i_agudo++;
                    break;
                case 'ó':
                    o_agudo++;
                    break;
                case 'ú':
                    u_agudo++;
                    break;
                case 'à':
                    a_grave++;
                    break;
                case 'è':
                    e_grave++;
                    break;
                case 'ì':
                    i_grave++;
                    break;
                case 'ò':
                    o_grave++;
                    break;
                case 'ù':
                    u_grave++;
                    break;
                case 'ã':
                    a_til++;
                    break;
                case 'õ':
                    o_til++;
                    break;
                case 'â':
                    a_circunflexo++;
                    break;
                case 'ê':
                    e_circunflexo++;
                    break;
                case 'î':
                    i_circunflexo++;
                    break;
                case 'ô':
                    o_circunflexo++;
                    break;
                case 'û':
                    u_circunflexo++;
                    break;
                case '<':
                    if(html.charAt(j+1) == 'b' && html.charAt(j+2) == 'r' && html.charAt(j+3) == '>'){
                        br++;
                        j+=3;
                    }else if(html.charAt(j+1) == 't' && html.charAt(j+2) == 'a' && html.charAt(j+3) == 'b' && html.charAt(j+4) == 'l' && html.charAt(j+5) == 'e' && html.charAt(j+6) == '>'){
                        table++;
                        j+=6;
                    }
                    break;
                default:
                    if(!(html.charAt(j) == 'a' || html.charAt(j) == 'e' || html.charAt(j) == 'i' || html.charAt(j) == 'o' || html.charAt(j) == 'u' || !((html.charAt(j) >= 97 && html.charAt(j) <= 122)))){
                        consoantes++;
                    }
                    break;
            }
        }

        return "a("+a+") e("+e+") i("+i+") o("+o+") u("+u+") á("+a_agudo+") é("+e_agudo+") í("+i_agudo+") ó("+o_agudo+") ú("+u_agudo+") à("+a_grave+") è("+e_grave+") ì("+i_grave+") ò("+o_grave+") ù("+u_grave+") ã("+a_til+") õ("+o_til+") â("+a_circunflexo+") ê("+e_circunflexo+") î("+i_circunflexo+") ô("+o_circunflexo+") û("+u_circunflexo+") consoante("+consoantes+") <br>("+br+") <table>("+table+")";
    }

    //FIM (reciclagem)
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //Main (reciclagem)
    public static void main(String[] args) {
        String[] entrada = new String[2000];
        int num_linha = 0;
        String html;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 1; i < num_linha; i += 2){
            html = getHtml(entrada[i]);
            System.out.println(readHtml(html) + " " + entrada[i-1]);
        }
   }
}
