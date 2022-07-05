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
    public String elemento;    
    public No esq, dir;     
    public int nivel;

    public No(String elemento) {
        this(elemento, null, null, 1);
    }

    public No(String elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }

    public static int getNivel(No no) { return (no == null) ? 0 : no.nivel; }

    public void setNivel(){ this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir)); }    
}

class AVL {
    private No raiz;  
    public int compara = 0;
    
    public AVL () {
		raiz = null;
	}

	public void inserir(String x) throws Exception {
		raiz = inserir(x, raiz);
	}

	private No inserir(String x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x.compareTo(i.elemento) < 0) {
            i.esq = inserir(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            i.dir = inserir(x, i.dir);
        } else { 
            throw new Exception("Erro ao inserir!");
        }
		return balancear(i);
	}

    private No balancear (No no) throws Exception {
        if (no != null){
            int fator = No.getNivel(no.dir) - No.getNivel(no.esq);
            
            if(Math.abs(fator) <= 1) {
                no.setNivel();
              
            } else if (fator == 2) {
                int fatorDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);
                
                if(fatorDir == -1) no.dir = rotacionarDir(no.dir);
                no = rotacionarEsq(no);
            } else if (fator == -2) {
                int fatorEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);

                if(fatorEsq == 1) no.esq = rotacionarEsq(no.esq);
                no = rotacionarDir(no);
            } else {
                throw new Exception("Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
            }
        }
        return no;
    }

    private No rotacionarDir(No no) {
		No noEsq = no.esq;
		No noEsqDir = noEsq.dir;

		noEsq.dir = no;
		no.esq = noEsqDir;
		no.setNivel(); 
		noEsq.setNivel(); 

		return noEsq;
	}

	private No rotacionarEsq(No no) {
		No noDir = no.dir;
		No noDirEsq = noDir.esq;

		noDir.esq = no;
		no.dir = noDirEsq;

		no.setNivel();
		noDir.setNivel();
		return noDir;
	}
    
	public void remover(String x) throws Exception {
        raiz = remover(x, raiz);
	}

	private No remover(String x, No i) throws Exception {
        if (i == null) {
            compara++;
            throw new Exception("Erro ao remover!");
        } else if (x.compareTo(i.elemento) < 0) {
            compara += 2;
            i.esq = remover(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            compara += 3;
            i.dir = remover(x, i.dir);  
        } else if (i.dir == null) {
            compara += 4;
            i = i.esq;

        } else if (i.esq == null) {
            compara += 5;
            i = i.dir;

        } else {
            compara += 5;
            i.esq = maiorEsq(i, i.esq);
        }

        return balancear(i);
	}

	private No maiorEsq(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento; 
            j = j.esq; 
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
          return j;
    }

	public void pesquisar(String x) {
        System.out.print(x + "\nraiz ");
        
        if(pesquisar(x, raiz)){
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
		
	}

	private boolean pesquisar(String x, No i) {
        boolean resp;
          if (i == null) {
           resp = false;
  
        } else if (x.compareTo(i.elemento) == 0) {
           resp = true;
        } else if (x.compareTo(i.elemento) < 0) {
            System.out.print("esq ");
           resp = pesquisar(x, i.esq);
        } else {
            System.out.print("dir ");
           resp = pesquisar(x, i.dir);
        }
        return resp;
      }
}

public class ArvAVL{
    public static boolean isFim (String str){
        return (str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M');
    } 
    public static void main (String[] args) throws Exception{

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

        
        AVL avl = new AVL();
        for(int i = 0; i < count; i++){
            avl.inserir(f[i].getOgTitle());
        }

        Film tmp = new Film();
        int qntI = Integer.parseInt(br.readLine());
        String[] str = new String[3];

        for(int i = 0; i < qntI; i++){
            line = br.readLine();
            str = line.split(" ", 2);

            if(str[0].charAt(0) == 'I'){
                
                tmp.ler(str[1]);

                avl.inserir(tmp.getOgTitle());

            } else if(str[0].charAt(0) == 'R'){
                avl.remover(str[1]);
            } 
        }

        // Pesquisa na ?rvore
        while(isFim (line = br.readLine()) == false){
            avl.pesquisar(line);
        }
    }
} 