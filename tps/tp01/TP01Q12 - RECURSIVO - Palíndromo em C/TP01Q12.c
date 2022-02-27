#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

//palindromo recursivo
bool palindromo(char str[], int i){

    if(i < strlen(str) - i - 2){
       if(str[i] != str[strlen(str)-i-2]){
           return 0;
        }
    }else{
        return 1;
    }

   return palindromo(str, i+1);
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
        if(palindromo(entrada[i], 0) == true){
            printf("SIM\n");
        }else{
            printf("NAO\n");
        }
    }

    return 0;
}
