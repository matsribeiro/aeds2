import java.io.File;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class TP01Q09{

    public static void Gravar(int num_linhas)throws Exception{
        RandomAccessFile entrada = new RandomAccessFile("arq09.txt", "rw");
        for(int i = 0; i < num_linhas; i++){
            entrada.seek(i * 8);
            entrada.writeDouble(MyIO.readDouble());
        }
        entrada.close();
    }

    public static void Ler(int num_linhas)throws Exception{
        RandomAccessFile entrada2 = new RandomAccessFile("arq09.txt", "rw");
        double num;
        num = entrada2.readDouble();
        for(int i = num_linhas-1 ; i >= 0; i--){
            entrada2.seek(i * 8);
            num = entrada2.readDouble();

            if(num % 1 == 0)
                MyIO.println((int)(num));
            else
                MyIO.println(num);
        }
        
        entrada2.close();
    }

    public static void main(String[] args) throws Exception {
        int num_linhas = MyIO.readInt();

        Gravar(num_linhas);
        Ler(num_linhas); 
    }
}
