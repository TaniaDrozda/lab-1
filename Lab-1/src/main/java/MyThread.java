public class MyThread extends Thread {
    private int[] A;
    private int i, j;
    public MyThread(int A[], int i, int j){
        this.A = A; this.i=i; this.j=j;
        ParallelBitTest.c_one = 0; ParallelBitTest.c_null = 0;
    }

    @Override
    public void run() {
        boolean[] b;
        for(int k=i; k<j; k++){
            b = ParallelBitTest.intTobitarray(A[k]);
            for(int m=0; m<b.length;m++){
                if(b[m]) ParallelBitTest.c_one++;
                else
                if (!b[m]) ParallelBitTest.c_null++;
            }
        }
    }
}
