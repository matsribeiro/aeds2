import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;
import java.util.Arrays;

class Film {
    // Attributes
    private String name;
    private String ogTitle;
    private Date releaseDate;
    private Integer duration;
    private String genre;
    private String ogLanguage;
    private String situation;
    private Float budget;
    private String[] arrKeyWds;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Constructors
    public Film() {
        this(null, null, null, null, null, null, null, null);
    }

    /**
     * @param name
     * @param ogTitle
     * @param releaseDate
     * @param duration
     * @param genre
     * @param ogLanguage
     * @param situation
     * @param budget
     */
    public Film(String name, String ogTitle, Date releaseDate, Integer duration, String genre, String ogLanguage, String situation, Float budget) {
        this.name = name;
        this.ogTitle = ogTitle;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.ogLanguage = ogLanguage;
        this.situation = situation;
        this.budget = budget;
        this.arrKeyWds = null;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOgLanguage() {
        return ogLanguage;
    }

    public void setOgLanguage(String ogLanguage) {
        this.ogLanguage = ogLanguage;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public String[] getArrKeyWds() {
        return arrKeyWds;
    }

    public void setArrKeyWds(String[] arrKeyWds) {
        this.arrKeyWds = arrKeyWds;
    }

    public Film clone(){
        Film cloned = new Film();

        cloned.name = this.name;
        cloned.ogTitle = this.ogTitle;
        cloned.releaseDate = this.releaseDate;
        cloned.duration = this.duration;
        cloned.genre = this.genre;
        cloned.ogLanguage = this.ogLanguage;
        cloned.situation = this.situation;
        cloned.budget = this.budget;
        cloned.arrKeyWds = this.arrKeyWds;

        return cloned;
    }


    /**
     * @param fileName
     */
    public void ler(String fileName){
        // Getting the right path for each read file
        String path = "/tmp/filmes/" + fileName;

        // Method that will split chunks of the read HTML and will assign the value to each Film's attribute
        splittingString(path);
    }

    private void splittingString(String path){
        // Data declaration
        String line = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            // Film name
            while(!reader.readLine().contains("title ott"));
            while(!reader.readLine().contains("h2"));
            this.name = removeTags(reader.readLine().trim());

            // Film release date
            while(!reader.readLine().contains("\"release\""));
            this.releaseDate = sdf.parse(removeTags(reader.readLine().trim()));

            // Film genre
            while(!reader.readLine().contains("genres"));
                // In this case, will use "line" because the last readLine will have the content that we want
            while(!(line = reader.readLine()).contains("<a href"));
            this.genre = removeTags(line).trim();

            // Film duration
            while(!reader.readLine().contains("runtime"));
            reader.readLine(); // Needed because an empty line was found
            this.duration = hoursToMinutes(reader.readLine().trim());

            // Film original title (if there is) & situation
            this.ogTitle = this.name;
            while( !(line = reader.readLine()).contains("Situação</bdi>") ) {
                if(line.contains("Título original")){
                    this.ogTitle = removeTags(line.replace("Título original", " ")).trim();
                }
            }
            this.situation = removeTags(line.replace("Situação", " ")).trim();

            // Film original language
            while( !(line = reader.readLine()).contains("Idioma original</bdi>") );
            this.ogLanguage = removeTags(line.replace("Idioma original", " ")).trim();

            // Film budget
            while( !(line = reader.readLine()).contains("Orçamento</bdi>") );
            String aux = removeTags(line.replace("Orçamento", " ")).trim();
            this.budget = (aux.equals("-")) ? 0.0F : convertBudget(aux);

            // Film key-words
            line = "";
            while( !reader.readLine().contains("Palavras-chave</bdi>") );
            while( !(line += reader.readLine().trim() + " ").contains("</ul>") );
            if(!line.contains("Nenhuma palavra-chave foi adicionada")){
                arrKeyWds = removeTags(line).trim().split("  ");
            }


        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("File cannot be read");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives a line that contains an HTML content and removes its tags
     * @param line
     * @return
     */
    private String removeTags(String line){
        // Data declaration
        String resp = "";
        int i = 0;

        /*
           The main idea here is to check if the char is equals to '<', if it's, it means that an HTML tag has opened
           So, CAN'T read anything until the tag is closed, '>' is found.
           It's also checking if any HTML special character (&....;) or if any "()" is found
           IF found, don't read anything until it has ended.
         */
        while (i < line.length()) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>') i++;
            }else {
                resp += line.charAt(i);
            }
            i++;
        }
        // Returning cleaned line
        return resp.replace("&nbsp;", "");
    }

    /**
     * Receives a String that contains hours, and convert it to minutes (Integer)
     * @param value
     * @return
     */
    private int hoursToMinutes(String value){
        // Data declaration
        int result = 0, minutes = 0;

        String[] splitValue = value.split(" ");
        if(splitValue.length > 1) {
            int hour = Integer.parseInt(removeLetters(splitValue[0]));
            minutes = Integer.parseInt(removeLetters(splitValue[1]));
            result = (60 * hour) + minutes;
        } else {
            if(splitValue[0].contains("h")){
                minutes = Integer.parseInt(removeLetters(splitValue[0]))*60;
            } else {
                minutes = Integer.parseInt(removeLetters(splitValue[0]));
            }
            result = minutes;
        }
        return result;
    }

    /**
     * Receives a String that contains hours, and leave only the numbers (ex: 1h 49m = 1 49)
     * @param value
     * @return
     */
    private String removeLetters(String value){
        // Data declaration
        String result = "";

        for(int i = 0; i < value.length(); i++){
            // If char is a number, a blank space, or a '.' (Used on convertBudget), will be stored into "result"
            if( (value.charAt(i) >= 48 && value.charAt(i) <= 57) || value.charAt(i) == ' ' || value.charAt(i) == '.')
                result += value.charAt(i);
        }
        return result;
    }

    /**
     * Receives a String that contains a FLOAT number, and converts it to a FLOAT number
     * (PS: It's necessary to remove few characters because String has ',' on it)
     * @param value
     * @return
     */
    private Float convertBudget(String value){
        return Float.parseFloat(removeLetters(value));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append(" ").append(ogTitle);
        sb.append(" ").append(sdf.format(getReleaseDate()));
        sb.append(" ").append(duration);
        sb.append(" ").append(genre);
        sb.append(" ").append(ogLanguage);
        sb.append(" ").append(situation);
        sb.append(" ").append(budget);
        sb.append(" ").append(arrKeyWds == null ? "[]" : Arrays.asList(arrKeyWds).toString());
        return sb.toString();
    }

    public void imprimir(){
        System.out.println(this.toString());
    }
}

class No {
    public char elemento;    // o conteúdo é um inteiro positivo
    public No esq, dir;     // Filhos da esquerda e da direita
    public No2 outro;

    public No(char elemento) {
        this.elemento = elemento;
		this.esq = this.dir = null;
        this.outro = null;
    }

    public No(char elemento, No esq, No dir) {
        this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
        this.outro = null;
    }
}

class No2 {
    public String elemento;    // o conteúdo é um inteiro positivo
    public No2 esq, dir;     // Filhos da esquerda e da direita

    public No2(String elemento) {
        this(elemento, null, null);
    }

    public No2(String elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;   // Raiz da Árvore
    public int compara = 0;
    /**
     * Construtor da classe
     */
    public ArvoreBinaria () throws Exception {
		raiz = null;
        inserir('D');
        inserir('R');
        inserir('Z');
        inserir('X');
        inserir('V');
        inserir('B');
        inserir('F');
        inserir('P');
        inserir('U');
        inserir('I');
        inserir('G');
        inserir('E');
        inserir('J');
        inserir('L');
        inserir('H');
        inserir('T');
        inserir('A');
        inserir('W');
        inserir('S');
        inserir('O');
        inserir('M');
        inserir('N');
        inserir('K');
        inserir('C');
        inserir('Y');
        inserir('Q');
	}

    /*************** MÉTODOS DE INSERÇÃO DO CHAR ***************/
    /**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(char x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(char x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else { 
            throw new Exception("Erro ao inserir!");
        }
		return i;
	}
    /*************** MÉTODOS DE INSERÇÃO DA STRING ***************/
    public void inserir(String s) throws Exception {
        inserir(s, raiz);
    }
  
    public void inserir(String s, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao inserir: caractere invalido!");
        } else if (s.charAt(0) < i.elemento) {
            inserir(s, i.esq);
        } else if (s.charAt(0) > i.elemento) {
           inserir(s, i.dir);
        } else {
           i.outro = inserir(s, i.outro);
        }
    }

    private No2 inserir(String y, No2 i) throws Exception {
        if (i == null) {
            i = new No2(y);
        } else if (y.compareTo(i.elemento) < 0) {
            i.esq = inserir(y, i.esq);
        } else if (y.compareTo(i.elemento) > 0) {
            i.dir = inserir(y, i.dir);
        } else {
            throw new Exception("Erro ao inserir: elemento existente!");
        }
		return i;
	}

    /*************** MÉTODOS DE PESQUISA ***************/
    /**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String x) {
        System.out.println("=> " + x);
        System.out.print("raiz ");
        return pesquisar(x, raiz);
	}

    /**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(String x, No no) {
        boolean resp = false;
        
        if (no != null) {
            compara++;
            resp = pesquisarS2(x, no.outro);

            if (resp == false) {
                compara += 2;
                System.out.print(" ESQ ");
                resp = pesquisar(x, no.esq);
            }
            if (resp == false) {
                compara += 3;
                System.out.print(" DIR ");
                resp = pesquisar(x, no.dir);
            }
        } 
        

        return resp;
    }

    private boolean pesquisarS2(String x, No2 no) {
        boolean resp;
        if (no == null) {
            compara++;
            resp = false;
        } else if (x.compareTo(no.elemento) < 0) {
            compara += 2;
            System.out.print("esq ");
            resp = pesquisarS2(x, no.esq); 
        } else if (x.compareTo(no.elemento) > 0) {
            compara += 3;
            System.out.print("dir ");
            resp = pesquisarS2(x, no.dir); 
        } else {
            compara += 3; 
            resp = true; 
        }

        return resp;
    }
}

public class ArvArv{
    /**
     * Receives a string and checks if it's equals to "FIM"
     * @param str
     * @return
     */
    public static boolean isFim (String str){
        return (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M');
    } 

    /**
     * (MAIN) receives an input from keyborad and starts the program
     * @param String[] args
     * @throws ParseException
     * @return
    */
    public static void main (String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
        String[] in = new String[1000];
        int count = 0;
        String line; 

        while(isFim (line = br.readLine()) == false){
            in[count] = line;
            count++;
        }
        
        Film f[] = new Film[count];
        for(int i = 0; i < count; i++){
            f[i]= new Film();
            f[i].ler(in[i]);
        }
        
        ArvoreBinaria ab = new ArvoreBinaria();
        for(int i = 0; i < count; i++){
            ab.inserir(f[i].getOgTitle());
        }

        Film tmp = new Film();
        int qntI = Integer.parseInt(br.readLine());
        String[] str = new String[3];

        for(int i = 0; i < qntI; i++){
            line = br.readLine();
            str = line.split(" ", 2);
            tmp.ler(str[1]);
            ab.inserir(tmp.getOgTitle());
        }

        while(isFim (line = br.readLine()) == false){
            if (ab.pesquisar(line)) System.out.println(" SIM");
            else System.out.println(" NAO");
        }
    } 
} 