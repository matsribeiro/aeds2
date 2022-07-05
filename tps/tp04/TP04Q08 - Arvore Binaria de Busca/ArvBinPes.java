import java.util.Scanner;

class No {
    public int elemento;
    public No esq, dir;

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(int x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;

        } else if (x == i.elemento) {
            resp = true;

        } else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);

        } else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void caminharCentral() {
        caminharCentral(raiz);
        System.out.println("");
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            System.out.print(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre() {
        caminharPre(raiz);
        System.out.println("");
    }

    private void caminharPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos() {
        caminharPos(raiz);
        System.out.println("");
    }

    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.print(i.elemento + " ");
        }
    }

    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);

        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);

        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }

        return i;
    }

    public void remover(int x) throws Exception {
        raiz = remover(x, raiz);
    }

    private No remover(int x, No i) throws Exception {

        if (i == null) {
            throw new Exception("Erro ao remover!");

        } else if (x < i.elemento) {
            i.esq = remover(x, i.esq);

        } else if (x > i.elemento) {
            i.dir = remover(x, i.dir);

        } else if (i.dir == null) {
            i = i.esq;

        } else if (i.esq == null) {
            i = i.dir;

        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return i;
    }

    private No maiorEsq(No i, No j) {

        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;

        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }
}

public class ArvBinPes{
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int c = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < c; i++) {
            ArvoreBinaria arvore = new ArvoreBinaria();
            String n = sc.nextLine().trim();
            String tmp = sc.nextLine();
            String[] array = tmp.split(" ");
    
            for (int j = 0; j < Integer.parseInt(n); j++) {
                arvore.inserir(Integer.parseInt(array[j]));
            }
            System.out.println("Case " + (i + 1) + ":");
            System.out.print("Pre.: ");
            arvore.caminharPre();
            System.out.print("In..: ");
            arvore.caminharCentral();
            System.out.print("Post: ");
            arvore.caminharPos();
            System.out.println("");
        }
        sc.close();
    }
}