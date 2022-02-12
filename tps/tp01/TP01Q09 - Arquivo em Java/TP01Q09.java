import java.io.File;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class TP01Q09{

    public static void main(String[] args) throws Exception {
        int num_linhas;
        double num;
        RandomAccessFile entrada = new RandomAccessFile("entrada.txt", "rw");
        num_linhas = MyIO.readInt();

        for(int i = 0; i < num_linhas; i++){
            entrada.seek(i * 8);
            entrada.writeDouble(MyIO.readDouble());
        }

        entrada = new RandomAccessFile("entrada.txt", "r");
        num = entrada.readDouble();
        for(int i = num_linhas-1 ; i >= 0; i--){
            entrada.seek(i * 8);
            num = entrada.readDouble();

            if(num % 1 == 0)
                MyIO.println((int)(num));
            else
                MyIO.println(num);
        }
        
        entrada.close();
    }
}
