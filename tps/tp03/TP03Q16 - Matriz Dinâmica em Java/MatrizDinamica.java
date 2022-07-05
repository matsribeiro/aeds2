import java.io.*;
import java.util.*;
import static java.lang.Integer.*;

public class MatrizDinamica {
    private Celula inicio;
    private int linha, coluna;

    public MatrizDinamica(){
        this(3,3);
    }

    public MatrizDinamica(int l, int c){
        this(new Celula(), l, c);
    }

    public MatrizDinamica(Celula inicio, int l, int c){
        this.inicio = inicio;
        this.linha = l;
        this.coluna = c;
        Celula aux, novaCelula, aux_linha;

        aux = inicio;
        for(int i = 0; i < coluna - 1; i++){
            aux.dir = new Celula();
            aux.dir.esq = aux;
            aux = aux.dir;
        }

        aux = inicio;
        for(int i = 0; i < linha - 1; i++){
            aux.inf = new Celula();
            aux.inf.sup = aux;
            aux = aux.inf;
        }

        aux = aux_linha = inicio;
        for(int i = 0; i < linha - 1; i++){
            for(int j = 0; j < coluna - 1; j++){
                aux.dir.inf = novaCelula = new Celula();
                novaCelula.sup = aux.dir;
                novaCelula.esq = aux.inf;
                aux.inf.dir = novaCelula;
                aux = aux.dir;
            }
            aux_linha = aux_linha.inf;
            aux = aux_linha;
        }
    }

    public MatrizDinamica somar(MatrizDinamica m){
        MatrizDinamica resp = new MatrizDinamica(this.linha, this.coluna);
        Celula soma, a, b;
        soma = resp.inicio;
        a = this.inicio;
        b = m.inicio;

        for(int i = 0; i < linha; i++){
            for(int j = 0; j < coluna; j++){
                soma.valor = a.valor + b.valor;
                soma = soma.dir;
                a = a.dir;
                b = b.dir;
            }
            soma = soma.inf;
            a = a.inf;
            b = b.inf;
        }

        return soma;
    }
}

class Celula {
    public Celula sup;
    public Celula inf;
    public Celula dir;
    public Celula esq;
    public int valor;
  
    public Celula(){
       this(0);
    }
  
    public Celula(int elemento){
       this(elemento, null, null, null, null);
    }
  
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
       this.elemento = elemento;
       this.inf = inf;
       this.sup = sup;
       this.esq = esq;
       this.dir = dir;
    }


}