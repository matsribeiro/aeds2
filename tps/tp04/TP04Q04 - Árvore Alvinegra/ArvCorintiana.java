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
    public boolean cor;

    public No() {  
    }

    public No(String elemento) {
        this(elemento, null, null, false);
    }

    public No(String elemento, boolean cor) {
        this(elemento, null, null, cor);
    }

    public No(String elemento, No esq, No dir, boolean cor) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.cor = cor;
    }
}

class Corintiana {
    private No raiz;  
    public int compara = 0;
    public Corintiana () {
		raiz = null;
	}

	public void inserir(String x) throws Exception {
        if (raiz == null) {
            raiz = new No(x);
        } else if (raiz.esq == null && raiz.dir == null){
            if (x.compareTo(raiz.elemento) < 0) {
                raiz.esq = new No(x); 
            } else {
                raiz.dir = new No(x);   
            }
        
        } else if (raiz.esq == null) {
            if (x.compareTo(raiz.elemento) < 0) {
                raiz.esq = new No(x);
            
            } else if (x.compareTo(raiz.dir.elemento) < 0) {
                raiz.esq = new No(raiz.elemento);
                raiz.elemento = x;
            } else {
                raiz.esq = new No(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = x;
            }
            
            raiz.esq.cor = raiz.dir.cor = false;
        
        } else if (raiz.dir == null) {
            if (x.compareTo(raiz.elemento) > 0) {
                raiz.dir = new No(x);
            
            } else if (x.compareTo(raiz.esq.elemento) > 0) {
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = x;
            } else {
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = x;
            }
            
            raiz.esq.cor = raiz.dir.cor = false;
        } else { 
            inserir(x, null, null, null, raiz);
        }

        raiz.cor = false;
	}
    
    private void balancear(No bisavo, No avo, No pai, No i) {
        if (pai.cor == true) {
            if (pai.elemento.compareTo(avo.elemento) > 0) { 
                if (i.elemento.compareTo(pai.elemento) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { 
                if (i.elemento.compareTo(pai.elemento) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.elemento.compareTo(bisavo.elemento) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        }
    }

    private void inserir(String elemento, No bisavo, No avo, No pai, No i) throws Exception {
        if (i == null) {
            if (elemento.compareTo(pai.elemento) < 0) {
                i = pai.esq = new No(elemento, true);
            } else {
                i = pai.dir = new No(elemento, true);
            }
            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        } else {
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (elemento.compareTo(i.elemento) < 0) {
                inserir(elemento, avo, pai, i, i.esq);
            } else if (elemento.compareTo(i.elemento) > 0) {
                inserir(elemento, avo, pai, i, i.dir);
            }
        }
    }

    private No rotacaoDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;
  
        noEsq.dir = no;
        no.esq = noEsqDir;
  
        return noEsq;
    }
  
    private No rotacaoEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;
  
        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }
  
    private No rotacaoDirEsq(No no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }
  
    private No rotacaoEsqDir(No no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
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

public class ArvCorintiana{
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

        Corintiana arvore = new Corintiana();
        for(int i = 0; i < count; i++){
            arvore.inserir(f[i].getOgTitle());
        }

        while(isFim (line = br.readLine()) == false){
            arvore.pesquisar(line);
        }
    } 
} 