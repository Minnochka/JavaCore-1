import java.util.Arrays;

import static java.lang.Math.round;

public class ArrayStream {
    private int length;
    private float[] arr;

    public int getLength() {
        return length;
    }

    public ArrayStream(int length){
        this.length = length;
        arr = new float[length];
        Arrays.fill(arr, 1);
    }

    public long CreateNewArr(int stream_cnt){
        long res;
        Thread[] th = new Thread[stream_cnt];
        long a = System.currentTimeMillis();

        if(stream_cnt == 1){
            for(int i = 0; i < length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }
        else{
            float[][] arr1 = new float[stream_cnt][];
            for(int i = 0; i < stream_cnt; i++){
                arr1[i] = new float[round(((float)length/stream_cnt) * (i + 1)) - round(((float)length/stream_cnt) * i)];
                System.arraycopy(arr, round((float)length/stream_cnt * i), arr1[i], 0, round(((float)length/stream_cnt) * (i + 1)) - round(((float)length/stream_cnt) * i));
            }

            for (int i = 0; i < stream_cnt; i++) {
                int I = i;
                int delta = round((float) length / stream_cnt * i);
                th[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < arr1[I].length; j++) {
                            arr1[I][j] = (float) (arr1[I][j] * Math.sin(0.2f + (delta + j) / 5) * Math.cos(0.2f + (delta + j) / 5) * Math.cos(0.4f + (delta + j) / 2));
                        }
                    }
                });
                th[i].start();
            }

            for (int i = 0; i < stream_cnt; i++) {
                try {
                    th[i].join();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            for (int i = 0; i < stream_cnt; i++) {
                System.arraycopy(arr1[i], 0, arr, round((float)length/stream_cnt * i), round(((float)length/stream_cnt) * (i + 1)) - round(((float)length/stream_cnt) * i));
            }
        }
        res = System.currentTimeMillis() - a;

        return res;
    }

}
