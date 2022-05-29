#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#define NUMENTRADA 1000
#define TAMLINHA 1000

// variaveis
typedef struct  {
    char nome[100];
    char titulo_original[100];
    char data[100];
    int duracao;
    char genero[100];
    char idioma[100];
    char situacao[100];
    char orcamento[100];
    char palavras_chave[100];
} Filme;


// construtores
Filme inicializaFilme(){
    Filme filme;
    strcpy(filme.nome, "");
    strcpy(filme.titulo_original, "");
    strcpy(filme.data, "");
    filme.duracao = 0;
    strcpy(filme.genero, "");
    strcpy(filme.idioma, "");
    strcpy(filme.situacao, "");
    strcpy(filme.orcamento, "");
    strcpy(filme.palavras_chave, "");

    return filme;
}

Filme inicializa(char nome[], char titulo_original[], char data[], int duracao, char genero[], char idioma[], char situacao[], char orcamento[], char palavras_chave[]){
    Filme filme;
    strcpy(filme.nome, nome);
    strcpy(filme.titulo_original, titulo_original);
    strcpy(filme.data, data);
    filme.duracao = duracao;
    strcpy(filme.genero, genero);
    strcpy(filme.idioma, idioma);
    strcpy(filme.situacao, situacao);
    strcpy(filme.orcamento, orcamento);
    strcpy(filme.palavras_chave, palavras_chave);

    return filme;
}

// getters e setters
void setNome(Filme *filme, char nome[]){
    strcpy(filme->nome, nome);
}
char *getNome(Filme filme){
    char *a;
    strcpy(a, filme.nome);
    return a;
}

void setTituloOriginal(Filme *filme, char titulo_original[]){
    strcpy(filme->titulo_original, titulo_original);
}
char *getTituloOriginal(Filme filme){
    char *a;
    strcpy(a, filme.titulo_original);
    return a;
}

void setData(Filme *filme, char data[]){
    strcpy(filme->data, data);
}
char *getData(Filme filme){
    char *a;
    strcpy(a, filme.data);
    return a;
}

void setDuracao(Filme *filme, int duracao){
    filme->duracao = duracao;
}
char getDuracao(Filme filme){
    return filme.duracao;
}

void setGenero(Filme *filme, char genero[]){
    strcpy(filme->genero, genero);
}
char *getGenero(Filme filme){
    char *a;
    strcpy(a, filme.genero);
    return a;
}

void setIdioma(Filme *filme, char idioma[]){
    strcpy(filme->idioma, idioma);
}
char *getIdioma(Filme filme){
    char *a;
    strcpy(a, filme.idioma);
    return a;
}

void setSituacao(Filme *filme, char situacao[]){
    strcpy(filme->situacao, situacao);
}
char *getSituacao(Filme filme){
    char *a;
    strcpy(a, filme.situacao);
    return a;
}

void setOrcamento(Filme *filme, char orcamento[]){
    strcpy(filme->orcamento, orcamento);
}
char *getOrcamento(Filme filme){
    char *a;
    strcpy(a, filme.orcamento);
    return a;
}

void setPalavrasChave(Filme *filme, char palavras_chave[]){
    strcpy(filme->palavras_chave, palavras_chave);
}
char *getPalavrasChave(Filme filme){
    char *a;
    strcpy(a, filme.palavras_chave);
    return a;
} 

char *limpaTag(char *old){
    char *newLine = (char *)malloc(sizeof(char) * strlen(old));
    int i = 0, j = 0;
    while (i < strlen(old))
    {
        if (old[i] == '<'){
            i++;
            while (old[i] != '>'){
                i++;
            }
                
        }
        else
        {
            newLine[j] = old[i];
            j++;
        }
        i++;
    }
    newLine[j] = '\0';
    return newLine;
}

char *trimString(char *str){
    char *end;

    while(isspace((unsigned char)*str)) str++;

    if(*str == 0)
        return str;

    end = str + strlen(str) - 1;
    while(end > str && isspace((unsigned char)*end)) end--;

    end[1] = '\0';

    return str;
}

void ler(Filme *Filme, char *nome){
    char filePath[200];
	sprintf(filePath, "/tmp/filmes/%s", nome);
    FILE *fp = fopen(filePath, "r"); 

    char buf[3000];
    while(!strstr(buf, "<title>")){
       fgets(buf, 3000, fp);
    }
    strcpy(Filme->nome,limpaTag(buf));
    Filme->nome[strcspn(Filme->nome, "\n")] = ' ';

    fclose(fp);
}

void imprimir(Filme filme){
    printf("%s\n", filme.nome);
}

int isFim(char s[]){
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main()
{
    char entrada[200][200];
    int numEntrada = 0;
    Filme filmes[61];
    
    do{
        scanf(" %[^\n]s", entrada[numEntrada]);
    } while (isFim(entrada[numEntrada++]) == 0);
    numEntrada--;
    
    for (int i = 0; i < numEntrada; i++){
        filmes[i] = inicializaFilme();
        ler(&filmes[i], entrada[i]);
        imprimir(filmes[i]);
    }

    return 0;
}