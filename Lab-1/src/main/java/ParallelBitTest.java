import java.io.*;
import java.text.NumberFormat;
import java.util.Scanner;
import java.lang.InterruptedException;
public class ParallelBitTest {
    int A[];
    int number_count;   int threads;
    public static int c_one, c_null;
    public ParallelBitTest() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("THREADS ");
        threads = in.nextInt();
    }
    void CreateArray() {
        A = new int[1000];
        for(int i=0; i<1000; i++)
        {
            A[i] = (int)(-6000 + Math.random()*10000);
        }
    }
    public void WriteFile() throws IOException {
        CreateArray();
        FileWriter filewriter = new FileWriter(new File("numbers.txt"));
        int a  = 1000;
        filewriter.write(a + "\n");
        for (int i=0;i<a;++i)
            filewriter.write(A[i] + "\n");
        filewriter.flush();
    }
    public void ReadFile() throws IOException {
        String thisLine;
        BufferedReader br = new BufferedReader(new FileReader("numbers.txt"));
        thisLine = br.readLine();
        number_count = Integer.valueOf(thisLine);
        A = new int[number_count];
        for(int i=0;i<number_count; i++){
            thisLine = br.readLine();
            if(thisLine!=null)
                A[i] = Integer.valueOf(thisLine);
        }
    }
    public static boolean[] intTobitarray(int input){
        boolean[] bits = new boolean[32];
        for (int i = 31; i >= 0; i--) {
            bits[i] = (input  & (1 << i)) != 0;
        }
        return bits;
    }

    public static void main(String[] args) throws IOException {
        ParallelBitTest par = new ParallelBitTest();
        long before = System.currentTimeMillis();
        par.WriteFile();
        par.ReadFile();
        MyThread[] t = new MyThread[par.threads];

        for(int i=0; i<par.threads;i++){
            t[i] = new MyThread(par.A, i*par.number_count/par.threads, (i+1)*(par.number_count/par.threads));
            t[i].start();
        }

        try{
            for(int i=0; i<par.threads; i++){
                t[i].join();
            }
        }
        catch (InterruptedException e){
            System.out.println("Interrupted exception");
        }

        long after = System.currentTimeMillis();
        long time = after - before;
        double p = (double)c_null/(double)c_one;
        System.out.println("NUMBERS COUNT: " + par.number_count);
        NumberFormat incf = NumberFormat.getInstance();
        incf.setMaximumFractionDigits(5);
        System.out.println("P: " +  incf.format(p));
        System.out.println("Time " + time + " ms");

    }
}
