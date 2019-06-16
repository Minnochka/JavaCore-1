package PasswordHomework;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Password {
    private byte[] password =  new byte[32];

    public boolean createPassword(String text) {
        boolean result = false;
        byte[] data1 = new byte[0];
        byte[] text_res;
        String symbols = "!#$%&?@";
        MessageDigest messageDigest = null;

        Pattern pattern = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[" + symbols + "]))[0-9a-zA-Z" +symbols + "]{8,20}$");
        Matcher m = pattern.matcher(text);
        if (m.matches()) {
            try {
                data1 = text.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
            text_res = messageDigest.digest(data1);
            for (int i = 0; i < text_res.length; i++) {
                password[i] = text_res[i];
            }
            result = true;
            System.out.println("Ваш пароль принят!");

        } else {
            System.out.println("Пароль должен состоять из цифр, Заглавных и прописных латинских букв и специальных символов (" + symbols + "). Попробуйте еще раз.");
        }
        return result;
    }

    public boolean checkPassword(String text) {
        boolean result = false, status = true;
        byte[] data1 = new byte[0];
        byte[] text_res = new byte[0];
        MessageDigest messageDigest = null;
        try {
            data1 = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        text_res = messageDigest.digest(data1);

        for (int i = 0; i < text_res.length; i++) {
            for (int j = 0; j < password.length; j++) {
                if(text_res[i] != password[i] && i == j) {
                    status = false;
                }
            }
        }
        if (status){
            result = true;
            System.out.println("Введен правильный пароль. Доступ разрешен!");
        }
        else{
            System.out.println("Введен неправильный пароль. Доступ запрещен!");
        }
        return result;
    }
}
