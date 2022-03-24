import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

class Filme{
    //variaveis
    private String nome;
    private String titulo_original;
    private String data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private float orcamento;
    private String[] palavras_chaves;

    //construtores
    public Filme(){ 
        nome = "";
        titulo_original = "";
        data = "";
        duracao = 0;
        genero = "";
        idioma = "";
        situacao = "";
        orcamento = 0;
    }

    public Filme(String nome, String titulo_original, String data, int duracao, String genero, String idioma, String situacao, float orcamento, String[] palavras_chaves){
        this.nome = nome;
        this.titulo_original = titulo_original;
        this.data = data;
        this.duracao = duracao;
        this.genero = genero;
        this.idioma = idioma;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavras_chaves = palavras_chaves;
    }

    //getters e setters
    public String getName(){
        return nome;
    }
    public void setName(String nome){
        this.nome = nome;
    }

    public String getTitulo(){
        return titulo_original;
    }
    public void setTitulo(String titulo_original){
        this.titulo_original = titulo_original;
    }

    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }

    public int getDuracao(){
        return duracao;
    }
    public void setDuracao(int duracao){
        this.duracao = duracao;
    }

    public String getGenero(){
        return genero;
    }
    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getIdioma(){
        return idioma;
    }
    public void setIdioma(String idioma){
        this.idioma = idioma;
    }

    public String getSituacao(){
        return situacao;
    }
    public void setSituacao(String situacao){
        this.situacao = situacao;
    }

    public float getOrcamento(){
        return orcamento;
    }
    public void setOrcamento(float orcamento){
        this.orcamento = orcamento;
    }

    public String[] getPalavras(){
        return palavras_chaves;
    }
    public void setPalavras(String[] palavras_chaves){
        this.palavras_chaves = palavras_chaves;
    }

    //filme clone
    protected Filme clone(){
        Filme clone = new Filme();

        clone.nome = this.nome;
        clone.titulo_original = this.titulo_original;
        clone.data = this.data;
        clone.duracao = this.duracao;
        clone.genero = this.genero;
        clone.idioma = this.idioma;
        clone.situacao = this.situacao;
        clone.orcamento = this.orcamento;
        clone.palavras_chaves = this.palavras_chaves;

        return clone;
    }

    //imprimir
    private void imprimir(){
        System.out.println(nome + " ");
    }

    //ler o arquivo (filme)
    public void ler(String entrada){
        try{
            BufferedReader html = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/filmes/" + entrada)));

            //nome
            for(int i = 0; entrada.charAt(i) != '.'; i++){
                this.nome += entrada.charAt(i);
            }

            html.close();
        } catch(Exception erro){
            System.out.println(erro);
        }

    }

    //fim
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    //main
    public static void main(String[] args){
        MyIO.setCharset("UTF-8");
        Filme[] Filme = new Filme[100];
        String[] entrada = new String[10000];
        int num_linha = 0;

        do{
            entrada[num_linha] = MyIO.readLine();
        }while(isFim(entrada[num_linha++]) == false);
        num_linha--;

        for(int i = 0; i < num_linha; i++){
            Filme[i] = new Filme();
            Filme[i].ler(entrada[i]);
            Filme[i].imprimir();
        }
    }
}

/*Date lancamento

SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/aaaa");

lancamento = sdf.parse("15/02/2021");
*/