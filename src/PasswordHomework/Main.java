package PasswordHomework;


import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Password pass = new Password();
        boolean result_create = false, result_check = false;
        while (!result_create) {
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();
            result_create = pass.createPassword(text);
        }
        System.out.println("good!");
        while (!result_check) {
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();
            result_check = pass.checkPassword(text);
        }
        System.out.println("sehr gut!");
    }
}


