#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

bool isPalindromo(char str[], int index){

    if(index < strlen(str) - index - 2){
        //         printf("%c",str[index]);
        // printf("%c",str[strlen(str) - index - 2]);
       if(str[index] != str[strlen(str)-index-2]){
           return 0;
        }
    }else{
        return 1;
    }

   return isPalindromo(str, index+1);
}

int isFim(char s[]){
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main(){
    // setlocale(LC_ALL, "en_US.UTF-8");
    char entrada[1000][1000];
    int num_linha = 0;

    do{
        fgets(entrada[num_linha], 1000, stdin);
    } while (isFim(entrada[num_linha++]) == 0);
    num_linha--;

    for(int i = 0; i < num_linha; i++){
        if(isPalindromo(entrada[i], 0) == true){
            printf("SIM\n");
        }else{
            printf("NAO\n");
        }
    }

    return 0;
}
