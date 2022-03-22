class filme{
    private String nome;
    private String titulo_original;
    private String data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private float orcamento;
    private String[] palavras_chaves;

    public filme(String nome, String titulo_original, String data, int duracao, String genero, String idioma, String situacao, float orcamento, String[] palavras_chaves){
        this.nome = nome;
        this.titulo_original = titulo_original;
        this.data = data;
        this.duracao = duracao;
    }

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
}

/*Date lancamento

SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/aaaa");

lancamento = sdf.parse("15/02/2021");
*/