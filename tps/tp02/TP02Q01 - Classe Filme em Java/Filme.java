import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

class Filme {
    // variaveis
    private String nome;
    private String titulo_original;
    private String data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private String orcamento;
    private String[] palavras_chaves;

    // construtores
    public Filme() {
        nome = "";
        titulo_original = "";
        data = "";
        duracao = 0;
        genero = "";
        idioma = "";
        situacao = "";
        orcamento = "";
    }

    public Filme(String nome, String titulo_original, String data, int duracao, String genero, String idioma,
            String situacao, String orcamento, String[] palavras_chaves) {
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

    // getters e setters
    public String getName() {
        return nome;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo_original;
    }

    public void setTitulo(String titulo_original) {
        this.titulo_original = titulo_original;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }

    public String[] getPalavras() {
        return palavras_chaves;
    }

    public void setPalavras(String[] palavras_chaves) {
        this.palavras_chaves = palavras_chaves;
    }

    // filme clone
    protected Filme clone() {
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

    // imprimir
    private void imprimir() {
        System.out.println(nome + " " + titulo_original + " " + data + duracao + " " + genero + " " + idioma + " "
                + situacao + " " + orcamento);
    }

    // remover as tags do html
    public String removeTags(String linha) {
        String resp = "";
        String resp2 = "";

        for (int i = 0; i < linha.length(); i++) {
            // remove '<...>'
            if (linha.charAt(i) == '<') {
                while (linha.charAt(i) != '>') {
                    i++;
                }
            } // remove 'Título original'
            else if (linha.charAt(i) == 'T') {
                if (linha.charAt(i + 1) == 'í') {
                    i += 14;
                } else {
                    resp += linha.charAt(i);
                }
            } // remove '(BR)'
            else if (linha.charAt(i) == '(') {
                i++;
                if (linha.charAt(i) == 'B') {
                    i += 2;
                } // remove '(US)'
                else if (linha.charAt(i) == 'U') {
                    i += 2;
                } // remove '(AU)'
                else if (linha.charAt(i) == 'A') {
                    i += 2;
                } // remove '(RU)'
                else if (linha.charAt(i) == 'R') {
                    i += 2;
                }
            } // remove '&nbsp;'
            else if (linha.charAt(i) == '&') {
                if (linha.charAt(i + 1) == 'n') {
                    i += 5;
                } else {
                    resp += linha.charAt(i);
                }
            } // remove 'Idioma original'
            else if (linha.charAt(i) == 'I') {
                if (linha.charAt(i + 1) == 'd') {
                    i += 14;
                } else {
                    resp += linha.charAt(i);
                }
            } // remove 'Situação'
            else if (linha.charAt(i) == 'S') {
                if (linha.charAt(i + 1) == 'i') {
                    if (linha.charAt(i + 2) == 't'){
                        i += 6;
                    }
                } else {
                    resp += linha.charAt(i);
                }
            } // remove 'Orçamento'
            else if (linha.charAt(i) == 'O') {
                if (linha.charAt(i + 1) == 'r') {
                    i += 8;
                }
            } else if (linha.charAt(i) == '$') {
            } // grava a linha
            else {
                resp += linha.charAt(i);
            }
        }

        // retira o espaço do html antes do atributo
        for (int i = 0; i < resp.length(); i++) {
            if (resp.charAt(i) == ' ') {
                while (resp.charAt(i) == ' ') {
                    i++;
                }
                for (int n = i; n < resp.length(); n++) {
                    resp2 += resp.charAt(n);
                }
                i = resp.length();
            } else {
                i = resp.length();
            }
        }

        return resp2;
    }

    // transformar horas em minutos
    public int transformarDuracao(String linha) {
        int resp = 0;
        String minutos = "";

        // horas
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == 'h') {
                // 1 h = 60 m
                if (linha.charAt(i - 1) == '1') {
                    resp = 60;
                }
                // 2 h = 120 m
                if (linha.charAt(i - 1) == '2') {
                    resp = 120;
                }
                // 3 h = 180 m
                if (linha.charAt(i - 1) == '3') {
                    resp = 180;
                }
                // 4 h = 240 m
                if (linha.charAt(i - 1) == '4') {
                    resp = 240;
                }
            }
            // minutos
            if (linha.charAt(i) == 'm') {
                // minutos > 9
                if (linha.charAt(i - 2) != ' ') {
                    int numeroConvertido = Character.getNumericValue(linha.charAt(i - 2));
                    int numeroConvertido2 = Character.getNumericValue(linha.charAt(i - 1));

                    minutos = String.valueOf(numeroConvertido) + String.valueOf(numeroConvertido2);
                } // minutos < 9
                else {
                    int numeroConvertido2 = Character.getNumericValue(linha.charAt(i - 1));

                    minutos = String.valueOf(numeroConvertido2);
                }

                resp = resp + Integer.parseInt(minutos);
            }
        }

        return resp;
    }

    // transformar orcamento
    public String transformarOrcamento(String linha) {
        String resp = "";
        String resp2 = "";

        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '-') {
                resp2 = "0.0";
                return resp2;
            } else {
                if (linha.charAt(i) == ',') {
                } else if (linha.charAt(i) == '.') {
                    i = linha.length();
                } else {
                    resp += linha.charAt(i);
                }
            }
        }

        int i = 0;
        resp2 += resp.charAt(i);
        resp2 += ".";
        int tamanho = 0;
        for (int n = 1; n < resp.length(); n++) {
            if (resp.charAt(n) == '0') {
                tamanho++;
            } else {
                resp2 += resp.charAt(n);
                tamanho++;
            }
        }
        resp2 += "E";
        resp2 += String.valueOf(tamanho);

        return resp2;
    }

    // ler o arquivo (filme)
    public void ler(String entrada) {
        try {
            BufferedReader html = new BufferedReader(
                    new InputStreamReader(new FileInputStream("/tmp/filmes/" + entrada)));
            String linha = html.readLine();

            // nome
            for (int i = 0; entrada.charAt(i) != '.'; i++) {
                if (entrada.charAt(i) == '_') {
                    this.nome += ":";
                } else if (entrada.charAt(i) == '&') {
                    this.nome += "";
                } else {
                    this.nome += entrada.charAt(i);
                }
            }

            // procurar enquanto nao encontrar "</html>" (comando para fechar o arquivo em
            // html)
            while (linha.contains("</html>") == false) {
                linha = html.readLine();

                // grava o titulo original ("title ott")
                if (linha.contains("title ott")) {
                    linha = html.readLine();
                    linha = html.readLine();
                    linha = html.readLine();
                    linha = html.readLine();
                    linha = html.readLine();
                    this.titulo_original = removeTags(linha);
                }

                // grava o titulo original (">Título original<")
                if (linha.contains(">Título original<")) {
                    this.titulo_original = removeTags(linha);
                }

                // grava a data ("(BR)")
                if (linha.contains("(BR)")) {
                    this.data = removeTags(linha);
                }

                // grava a data ("(US)")
                if (linha.contains("(US)")) {
                    this.data = removeTags(linha);
                }

                // grava a data ("(AU)")
                if (linha.contains("(AU)")) {
                    this.data = removeTags(linha);
                }

                // grava a data ("(RU)")
                if (linha.contains("(RU)")) {
                    this.data = removeTags(linha);
                }

                // grava a duracao ("runtime")
                if (linha.contains("runtime")) {
                    linha = html.readLine();
                    linha = html.readLine();
                    this.duracao = transformarDuracao(linha);
                }

                // grava o genero ("genres")
                if (linha.contains("genres")) {
                    linha = html.readLine();
                    linha = html.readLine();
                    this.genero = removeTags(linha);
                }

                // grava o idioma (">Idioma original<")
                if (linha.contains(">Idioma original<")) {
                    this.idioma = removeTags(linha);
                }

                // grava o situacao (">Situação<")
                if (linha.contains(">Situação<")) {
                    this.situacao = removeTags(linha);
                }

                // grava o orcamento (">Orçamento<")
                if (linha.contains(">Orçamento<")) {
                    this.orcamento = removeTags(linha);
                    this.orcamento = transformarOrcamento(orcamento);
                }
            }

            html.close();
        } catch (Exception erro) {
            System.out.println(erro);
        }

    }

    // fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // main
    public static void main(String[] args) {
        MyIO.setCharset("UTF-8");
        Filme[] Filme = new Filme[100];
        String[] entrada = new String[10000];
        int num_linha = 0;

        do {
            entrada[num_linha] = MyIO.readLine();
        } while (isFim(entrada[num_linha++]) == false);
        num_linha--;

        for (int i = 0; i < num_linha; i++) {
            Filme[i] = new Filme();
            Filme[i].ler(entrada[i]);
            Filme[i].imprimir();
        }
    }
}