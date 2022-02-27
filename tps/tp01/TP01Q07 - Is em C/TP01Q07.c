#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Checa vogais
const char* Vogal(char s[]){

    for(int i = 0; i < strlen(s)-2; i++){
        if(s[i] != 'a' && s[i] != 'e' && s[i] != 'i' && s[i] != 'o' && s[i] != 'u'){
            return "NAO ";
        }
    }

    return "SIM ";
}

//Checa consoantes
const char* Consoante(char s[]){

    for(int i = 0; i < strlen(s)-2; i++){
        if(s[i] == 'a' || s[i] == 'e' || s[i] == 'i' || s[i] == 'o' || s[i] == 'u' || !((s[i] >= 97 && s[i] <= 122))) {
            return "NAO ";
        }
    }
    
    return "SIM ";
}

//Checa inteiros
const char* Inteiro(char s[]){

    for(int i = 0; i < strlen(s)-1; i++){
        if(!((s[i] >= 48 && s[i] <= 57))){
            return "NAO ";
        }
    }

    return "SIM ";
}

//Checa reais
const char* Real(char s[]){
    int virgula_ponto = 0;

    for(int i = 0; i < strlen(s)-2; i++){
        if((s[i] >= 48 && s[i] <= 57) == 0){
            if((s[i] == ',' || s[i] == '.') && virgula_ponto == 0)
                virgula_ponto++;
            else
                return "NAO";
        }
    }

    return "SIM";
}

//FIM (reciclagem)
int isFim(char s[]){
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

//Main (reciclagem)
int main(){
    char entrada[1000][1000];
    int num_linha = 0;

    do{
        fgets(entrada[num_linha], 1000, stdin);
    } while (isFim(entrada[num_linha++]) == 0);
    num_linha--;

    for(int i = 0; i < num_linha; i++){
        printf("%s%s%s%s\n", Vogal(entrada[i]), Consoante(entrada[i]), Inteiro(entrada[i]), Real(entrada[i]));
    }

    return 0;
}
