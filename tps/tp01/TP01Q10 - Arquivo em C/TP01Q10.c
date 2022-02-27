#include <stdio.h>
#include <stdlib.h>

int main(){
    FILE *fp = fopen("arq10.txt", "wb");
    int num_linhas;
    double num;
    scanf("%i", &num_linhas);

    if(fp != NULL){
        for(int i = 0; i < num_linhas; i++){
            scanf("%lf", &num);
            fwrite(&num, sizeof(double), 1, fp);
        }
    }else{
        printf("Erro\n");
    }

    fclose(fp);
    fp = fopen("arq10.txt", "rb");

    for(int i = 1; i <= num_linhas; i++){
        fseek(fp, -i*8, SEEK_END);
        fread(&num, sizeof(double), 1, fp);
        printf("%g\n", num);
    }

    fclose(fp);
    
    return 0;
}
