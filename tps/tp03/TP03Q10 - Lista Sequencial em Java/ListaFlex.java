import java.io.*;
import java.util.*;
import static java.lang.Integer.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ListaFlex {
    public Film first;     //Primeira celula da lista
    public Film last;      //Ultima celula da lista
    private int size;       //Quantidade de elementos na lista

    /* CONSTRUTORES */
    ListaFlex(){
        first = last = null;
        size = 0;
    }
    
    ListaFlex(Film obj){
        first = last = obj;
        size = 1;
    }

    /* METODOS DE INSERCAO */
    //Insere na primeira posicao da lista (first) e aponta a celula para o ultimo first
    public void inserirInicio(Film obj){
        if(size == 0){
            first = last = obj;
        }else{
            Film temp = first;
            first = obj;
            first.next = temp;
        }
        size++;
    }

    //Insere na ultima posicao da lista (last)
    public void inserirFim(Film obj){
        if(size == 0){
            first = last = obj;
        }else{
            last.next = obj;
            last = obj;
        }
        size++;
    }

    //Insere em uma posicao especificada da lista
    public void inserir(Film obj, int index){
        if(index == 0){
            inserirInicio(obj);
        }else if(index == this.size){
            inserirFim(obj);
        }else{
            Film i = first;
            for(int j = 0; j < index-1; j++, i = i.next);

            obj.next = i.next;
            i.next = obj;
            size++;
        }
    }

    /* METODOS DE REMOCAO */
    //Remove a primeira celula da lista (first) e retorna a celula removida
    public Film removerInicio(){
        Film removed = first;

        if(size == 1){
            first = last = null;
        }else{
            first = first.next;
        }

        size--;

        return removed;
    }

    //Remove a ultima celula da lista (last) e retorna a celula removida
    public Film removerFim(){
        Film removed;

        if(size == 1){
            removed = first;
            first = last = null;
        }else{
            Film i = first;
            for(int j = 0; j < size-2; j++, i = i.next);

            removed = i.next;
            last = i;
        }

        size--;
        return removed;
    }

    //Remove a celula na posicao espeficada da lista
    public Film remover(int index){
        Film removed;

        if(index == 0){
            removed = removerInicio();
        }else if(index == this.size - 1){
            removed = removerFim();
        }else{
            Film i = first;
            for(int j = 0; j < index-1; j++, i = i.next);

            removed = i.next;
            i.next = i.next.next;
            size--;
        }

        return removed;
    }

    /* METODOS MISCELANEOS */
    //Get de size (afinal este eh um atributo privado e bemm essencial para o funcionamento da classe)
    public int getSize() { return this.size; }

    //Checa se o input deve ser terminado
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    /* MAIN */
    public static void main (String[] args){
        ListaFlex lista = new ListaFlex();
        String linha = MyIO.readLine();
        Film obj;
        int qnt_operacoes, index;

        //Preenche a lista com todas as Films vindas antes do 1o "FIM"
        while(isFim(linha) == false){
            obj = new Film();
            obj.ler(linha);
            lista.inserirFim(obj);

            linha = MyIO.readLine();
        }

        //1a linha apos o "FIM" = quantidade de operacoes que serao realizadas
        qnt_operacoes = parseInt(MyIO.readLine());

        //Realiza todas as operacoes presentes nas seguintes qnt_operacoes linhas
        for(int i = 0; i < qnt_operacoes; i++) {
            linha = MyIO.readLine();

            if(linha.charAt(0) == 'I'){     // INSERCAO
                obj = new Film();
                
                if(linha.charAt(1) == 'I'){                     // II -> inserir no inicio
                    obj.ler(linha.replace("II ", ""));

                    lista.inserirInicio(obj);
                }
                else if(linha.charAt(1) == 'F'){                // IF -> inserir no fim
                    obj.ler(linha.replace("IF ", ""));

                    lista.inserirFim(obj);
                }
                else{                                           // I* -> inserir na posicao especificada
                    linha = linha.replace("I* ", "");
                    index = Integer.parseInt(linha.substring(0, 2));
    
                    obj.ler(linha.substring(3, linha.length()));
    
                    lista.inserir(obj, index);
                }
                
            }else{                          // REMOCAO
                if(linha.charAt(1) == 'I'){                     // RI -> remover do inicio
                    System.out.println("(R) " + lista.removerInicio().getName());
                }
                else if(linha.charAt(1) == 'F'){                // RF -> remover do fim
                    System.out.println("(R) " + lista.removerFim().getName());
                }
                else{                                           // R* -> remover da posicao especificada
                    linha = linha.replace("R* ", "");
                    index = Integer.parseInt(linha);

                    System.out.println("(R) " + lista.remover(index).getName());
                }
            }
        }

        //Printa as Celulas da lista a partir de first ate last
        obj = lista.first;
        int contador = 0;
        for(int i = 0; i < lista.getSize(); i++){
            System.out.print("[" + contador++ + "] ");
            obj.imprimir();
            obj = obj.next;
        }
    }
}

/**
 * @author Thiago de Campos Ribeiro Nolasco
 */
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
    public Film next;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Constructors
    public Film() {
        name = null;
        ogTitle = null;
        releaseDate = null;
        duration = null;
        genre = null;
        ogLanguage = null;
        situation = null;
        budget = null;
        next = null;
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
        this.next = null;
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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"))) {

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