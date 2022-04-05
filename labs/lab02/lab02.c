#include <stdio.h>
#include <stdbool.h>
#include <string.h>

/*const char* contaParentese(char s[]){
    int abre = 0;
    int fecha = 0;
    int i = 0;

    while(s[i] != '('){
        i++;
    }

    for(int n = i; n < strlen(s); n++){
        if(s[n] == '('){
            abre++;
        } else if (s[n] == ')'){
            fecha++;
        }
    }

    if(abre == fecha){
        return "correto";
    } else {
        return "incorreto";
    }
    
}*/

bool contaParentese(char *expressao){
    int cont = 0;
    for(int i = 0; i < strlen(expressao) && cont>=0; i++){
        if(expressao[i] == '(') cont++;
        else if(expressao[i] == ')') cont--;
    }
    return cont == 0;
}

int isFim(char s[]){
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main(){
    char entrada[1000][1000];
    int num_linha = 0;
    bool resp;

    do{
        fgets(entrada[num_linha], 1000, stdin);
    } while (isFim(entrada[num_linha++]) == 0);
    num_linha--;

    for(int i = 0; i < num_linha; i++){
        resp = contaParentese(entrada[i]);
        if(resp == true){
            printf("correto\n");
        } else {
            printf("incorreto\n");
        }
    }

    return 0;
}
