import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayStream arrStream = new ArrayStream(10000000);
        int stream_cnt;
        boolean status = false;
        System.out.println("Введите количество потоков (от 1 до " + arrStream.getLength() + "):");
        if (!sc.hasNextInt()) {
            System.out.println("Введенное значение не является целым числом!");
        }
        else{
            stream_cnt = sc.nextInt();
            if (stream_cnt < 1){
                System.out.println("Количество потоков не может быть отрицательным или 0!");
            }
            else if(stream_cnt > arrStream.getLength()){
                System.out.println("Количество потоков не может превышать длину массива!");
            }
            else{
                //System.out.println("stream_cnt = " + stream_cnt);
                System.out.println("time = " + arrStream.CreateNewArr(stream_cnt));
            }

        }

    }
}
