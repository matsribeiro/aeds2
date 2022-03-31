#include <stdio.h>

// variaveis
typedef struct Filme
{
    char nome[100];
    char titulo_original[100];
    char data[100];
    int duracao;
    char genero[100];
    char idioma[100];
    char situacao[100];
    char orcamento[100];
    char palavras_chaves[100];
} Filme;

// construtores
Filme filme(){
    Filme filme;
    strcpy(filme.nome, "");
    strcpy(filme.titulo_original, "");
    strcpy(filme.data, "");
    filme.duracao = 0;
    strcpy(filme.genero, "");
    strcpy(filme.idioma, "");
    strcpy(filme.situacao, "");
    strcpy(filme.orcamento, "");
    strcpy(filme.palavras_chaves, "");

    return filme;
}

Filme filme(char nome[], char titulo_original[], char data[], int duracao, char genero[], char idioma[], char situacao[], char orcamento[], char palavras_chaves[]){
    Filme filme;
    strcpy(filme.nome, nome);
    strcpy(filme.titulo_original, titulo_original);
    strcpy(filme.data, data);
    filme.duracao = duracao;
    strcpy(filme.genero, genero);
    strcpy(filme.idioma, idioma);
    strcpy(filme.situacao, situacao);
    strcpy(filme.orcamento, orcamento);
    strcpy(filme.palavras_chaves, palavras_chaves);

    return filme;
}