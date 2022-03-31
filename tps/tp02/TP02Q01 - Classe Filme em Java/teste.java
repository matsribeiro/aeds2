import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

//lab - dia 29/03
//discussao sobre tp02

class Filme{
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String nome;
    private int duracao;
    private Date data;
    private String palavrasChave[];

    //gets and sets
    public String getName(){
        return nome;
    }
    public void setName(String nome){
        this.nome = nome;
    }

    public int getDuracao(){
        return duracao;
    }
    public void setDuracao(int duracao){
        this.duracao = duracao;
    }

    public Date getData(){
        return data;
    }
    public void setData(Date data){
        this.data = data;
    }

    public String[] getPalavras(){
        return palavrasChave;
    }
    public void setPalavras(String palavras_chaves){
        this.palavrasChave = palavrasChave;
    }

    //metodo clone
    public Filme clonar(){
        Filme clone =  new Filme();
        clone.setData(data);
        clone.setDuracao(duracao);
        clone.setName(nome);
        return clone; 
    }

    //mrtodo imprimir
    public String imprimir(){
        return getName()+sdf.format(getData());
    }

    public String removeTags(String original){
        String limpa = "";

        for(int i = 0; i < original.length(); i++){
            if(original.charAt(i) == '<'){
                while(original.charAt(i) != '>') i++;
            }else{
                limpa += original.charAt(i);
            }
        }

        return limpa;
    }

    //metodo ler
    public void ler(String nomeArq) throws Exception{
        String caminho = "/tmp/filmes/";
        FileReader fr =  new FileReader(caminho+nomeArq);
        BufferedReader br = new BufferedReader(fr);
        //primeira linha
        String linha = br.readLine();
        //saltando linhas ate encontrar title
        while(!linha.contains("<title>")){
            linha = br.readLine();
        }

        //atributo o nome na varialvel nome
        setName(buscaAteParenteses(removeTags(linha)).trim());

        //saltando linhas ate encontrar title
        while(!linha.contains("\"realeadse\"")){
            linha = br.readLine();
        }
        linha = br.readLine();

        //setar a variavel data
        setData(sdf.parse(buscaAteParenteses(linha).trim()));
        String temporaria = buscaAteParenteses(linha).trim();

        //saltando linhas ate encontrar title
        while(!linha.contains("Palavras-chave")){
            linha = br.readLine();
        }
        String palavrasTemp[] = new String[30];
        int contador = 0;
        while(!linha.contains("</ul>")){
            linha = br.readLine();
            if(linha.contains("li")){
                //setar a palavra chave nesta linha em array temp
                palavrasTemp[contador++] = removeTags(linha).trim();
            }
        }
        contador = contador>0?contador-1:0;
        
        palavrasChave = new String[contador];

    } 

    public String buscaAteParenteses(String original){
        String limpa = "";

        for(int i = 0;original.charAt(i)!='(';i++){
            limpa += original.charAt(i);
        }

        return limpa;
    }

    public static void main(String[] args) throws Exception{
        //criar array de String
        String entrada[] = new String[100];
        int contador = 0;

        //preencher com entradas do pub.in
        String linha = MyIO.readLine();
        while(isFim == false){
            entrada[contador++] = linha;
            linha = MyIO.readLine();
        }
        contador--;

        //criar um array de filmes de tamanho entrada
        Filme filmes[] = new Filme[contador];

        //instanciar os objetos
        for(int i = 0; i<contador; i++){
            filmes[i] = new Filme();
            filmes[i] = ler(entrada[i]);
        }
    }
}

//contains em c -> int a = strstr(linha,"...");