package Lesson3.WordArray;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String[] arr = {"cat", "dog", "bird", "rabbit", "fish", "dog", "bird", "rabbit", "dog", "dog", "dog", "bird"};
        HashMap<String, Integer> arr_hm = new HashMap<String, Integer>();
        Integer cnt;
        for(int i = 0; i < arr.length; i++) {
            if (i == 0){
                System.out.print("Массив: " + arr[i]);
            }
            else{
                System.out.print(", " + arr[i]);
            }
            if(arr_hm.containsKey(arr[i])){
                cnt = arr_hm.get(arr[i]) + 1;
                arr_hm.put(arr[i], cnt);
            }
            else{
                arr_hm.put(arr[i],1);
            }
        }
        System.out.println();

        for (HashMap.Entry el : arr_hm.entrySet()) {
            System.out.println("Word: " + el.getKey() + " - " + el.getValue());
        }
    }
}
