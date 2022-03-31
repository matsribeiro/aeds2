import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;

class Filme {
    // variaveis
    private String nome;
    private String tituloOriginal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private float orcamento;
    private String palavrasChave[];

    // construtores
    public Filme() {
        nome = "";
        tituloOriginal = "";
        duracao = 0;
        genero = "";
        idioma = "";
        situacao = "";
        orcamento = 0;
    }

    public Filme(String nome, String tituloOriginal, Date data, int duracao, String genero, String idioma,
            String situacao, float orcamento, String palavrasChave[]) {
        this.nome = nome;
        this.tituloOriginal = tituloOriginal;
        this.data = data;
        this.duracao = duracao;
        this.genero = genero;
        this.idioma = idioma;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavrasChave = palavrasChave;
    }

    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    // metodo clone
    public Filme clonar() {
        Filme clone = new Filme();
        clone.setNome(nome);
        clone.setTituloOriginal(tituloOriginal);
        clone.setDuracao(duracao);
        clone.setGenero(genero);
        clone.setIdioma(idioma);
        clone.setSituacao(situacao);
        clone.setOrcamento(orcamento);
        clone.setPalavrasChave(palavrasChave);

        return clone;
    }

    // metodo ler
    public void ler(String nomeArq) {
        try {
            String caminho = "/tmp/filmes/";
            FileReader fr = new FileReader(caminho + nomeArq);
            BufferedReader br = new BufferedReader(fr);

            // primeira linha
            String linha = br.readLine();

            // saltando linhas ate encontrar <title>
            while (!linha.contains("<title>")) {
                linha = br.readLine();
            }

            // atributo title na variavel nome
            setNome(buscaAteParenteses(removeTags(linha)).trim());

            // saltando linhas ate encontrar "release"
            while (!linha.contains("\"release\"")) {
                linha = br.readLine();
            }
            linha = br.readLine();

            // atributo release na variavel data
            setData(sdf.parse(buscaAteParenteses(linha).trim()));

            // saltando linhas ate encontrar "genres"
            while (!linha.contains("\"genres\"")) {
                linha = br.readLine();
            }
            linha = br.readLine();
            linha = br.readLine();

            // atributo genres na variavel genero
            setGenero(limpaGenero(removeTags(linha)).trim());

            // saltando linhas ate encontrar "runtime"
            while (!linha.contains("\"runtime\"")) {
                linha = br.readLine();
            }
            linha = br.readLine();
            linha = br.readLine();

            // atributo runtime na variavel duracao
            setDuracao(transformarDuracao(linha));

            // saltando linhas ate encontrar >Título original<
            while (!linha.contains(">Título original<")) {
                linha = br.readLine();
            }

            // atributo Título original na variavel tituloOriginal
            setTituloOriginal(limpaTitulo(removeTags(linha)).trim());

        } catch (Exception erro) {
            System.out.println(erro);
        }
    }

    // metodo para remover as tags do html
    public String removeTags(String original) {
        String limpa = "";

        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == '<') {
                while (original.charAt(i) != '>')
                    i++;
            } else {
                limpa += original.charAt(i);
            }
        }

        return limpa;
    }

    // metodo para buscar ate encontrar algum parentese
    public String buscaAteParenteses(String original) {
        String limpa = "";

        for (int i = 0; original.charAt(i) != '('; i++) {
            if (original.charAt(i) == '&') {
                while (original.charAt(i) != ' ') {
                    i++;
                }
            }
            limpa += original.charAt(i);
        }

        return limpa;
    }

    // metodo para limpar "&nbsp;" das linhas do genero
    public String limpaGenero(String original) {
        String limpa = "";

        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == '&') {
                while (original.charAt(i) != ';') {
                    i++;
                }
            } else {
                limpa += original.charAt(i);
            }
        }

        return limpa;
    }

    // metodo para transformar horas em minutos
    public int transformarDuracao(String original) {
        int resp = 0;
        String minutos = "";

        // horas
        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == 'h') {
                // 1 h = 60 m
                if (original.charAt(i - 1) == '1') {
                    resp = 60;
                }
                // 2 h = 120 m
                if (original.charAt(i - 1) == '2') {
                    resp = 120;
                }
                // 3 h = 180 m
                if (original.charAt(i - 1) == '3') {
                    resp = 180;
                }
                // 4 h = 240 m
                if (original.charAt(i - 1) == '4') {
                    resp = 240;
                }
            }
            // minutos
            if (original.charAt(i) == 'm') {
                // minutos > 9
                if (original.charAt(i - 2) != ' ') {
                    int numeroConvertido = Character.getNumericValue(original.charAt(i - 2));
                    int numeroConvertido2 = Character.getNumericValue(original.charAt(i - 1));

                    minutos = String.valueOf(numeroConvertido) + String.valueOf(numeroConvertido2);
                } // minutos < 9
                else {
                    int numeroConvertido2 = Character.getNumericValue(original.charAt(i - 1));

                    minutos = String.valueOf(numeroConvertido2);
                }

                resp = resp + Integer.parseInt(minutos);
            }
        }

        return resp;
    }

    // metodo para limpar "Título original" das linhas do tituloOriginal
    public String limpaTitulo(String original) {
        String limpa = "";

        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == 'T') {
                if (original.charAt(i + 1) == 'í') {
                    i += 14;
                } else {
                    limpa += original.charAt(i);
                }
            } else {
                limpa += original.charAt(i);
            }
        }

        return limpa;
    }

    // metodo imprimir
    public void imprimir() {
        System.out.println(nome + " " + tituloOriginal + " " + sdf.format(data) + " " + duracao + " " + genero);
    }

    // fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // main
    public static void main(String[] args) {
        MyIO.setCharset("UTF-8");
        String[] entrada = new String[100];
        int contador = 0;

        // preencher com entradas do pub.in
        do {
            entrada[contador] = MyIO.readLine();
        } while (isFim(entrada[contador++]) == false);
        contador--;

        // criar um array de filmes de tamanho entrada
        Filme filmes[] = new Filme[contador];

        // instanciar os objetos
        for (int i = 0; i < contador; i++) {
            filmes[i] = new Filme();
            filmes[i].ler(entrada[i]);
            filmes[i].imprimir();
        }
    }
}
