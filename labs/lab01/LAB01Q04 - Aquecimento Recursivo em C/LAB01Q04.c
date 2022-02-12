#include <stdio.h>
#include <string.h>
#include <ctype.h>

int isMaiuscula(char str[], int pos){

    int maiuscula = 0;

    if(pos < strlen(str)){
        if(isupper(str[pos])){
            maiuscula = 1 + isMaiuscula (str, pos + 1);
        } else {
            maiuscula = isMaiuscula (str, pos + 1);
        }
    }

   return maiuscula;
}

int isFim(char s[]){
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main(){
    char entrada[1000][1000];
    int num_linha = 0;

    do{
        fgets(entrada[num_linha], 1000, stdin);
    } while (isFim(entrada[num_linha++]) == 0);
    num_linha--;

    for(int i = 0; i < num_linha; i++){
        int x = isMaiuscula(entrada[i], 0);
        printf("%d\n",x);
    }

    return 0;
}
