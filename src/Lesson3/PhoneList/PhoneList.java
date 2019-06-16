package Lesson3.PhoneList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneList {
    private HashMap<String, HashSet<String>> phoneList;

    public PhoneList(){
        phoneList = new HashMap<String, HashSet<String>>();
        HashSet<String> str1 = new HashSet<String>();
        HashSet<String> str2 = new HashSet<String>();
        HashSet<String> str3 = new HashSet<String>();
        str1.add("9034534212");
        str1.add("9034890011");
        str1.add("9457642112");
        phoneList.put("КОХОНОВ", str1);
        str2.add("9127632885");
        str2.add("9058799550");
        str2.add("9047312896");
        str2.add("9078965319");
        str2.add("9564312400");
        phoneList.put("САЛЬМАН", str2);
        str3.add("9765431234");
        str3.add("9875435342");
        phoneList.put("ГРИГОРЬЕВ", str3);
    }

    public void getInfo(){
        System.out.println("Данные справочника:");
        for(HashMap.Entry el: phoneList.entrySet()){
            System.out.println(el.getKey() + ": " + el.getValue());
        }
    }

    public void get(String surname){
        HashSet<String> phones;
        //System.out.println("Введите фамилию:");
        //Scanner sc = new Scanner(System.in);
        //String surname = sc.nextLine();
        System.out.println("Ищем фамилию " + surname);
        if(phoneList.containsKey(surname.toUpperCase())){
            phones = phoneList.get(surname.toUpperCase());
            System.out.println(phones);
        }
        else{
            System.out.println("В справочнике нет такой фамилии");
        }
    }

    public void add(String surname, String phone){
        HashSet<String> phones;
        Pattern pattern = Pattern.compile("^(8|\\+7|7)?\\(?[0-9]{3}\\)?-?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$");
        Pattern patternFIO = Pattern.compile("^[A-ZА-Я]{2,}$");
        Matcher m;
        System.out.println("добавляем фамилию " + surname + " и телефон " + phone);
        //System.out.println("Введите фамилию:");
        //Scanner sc = new Scanner(System.in);
        //String surname = sc.nextLine();

        if(phoneList.containsKey(surname.toUpperCase())){
            phones = phoneList.get(surname.toUpperCase());
            //System.out.println("Введите телефон:");
            //String phone = sc.nextLine();
            m = pattern.matcher(phone);
            if(m.matches()){
                phone = getStandardPhone(phone);
                phones.add(phone);
                phoneList.put(surname.toUpperCase(), phones);
            }
            else{
                System.out.println("Телефон введен неправильно");
            }
        }
        else{
            m = patternFIO.matcher(surname.toUpperCase());
            if(m.matches()){
                //System.out.println("Введите телефон:");
                //String phone = sc.nextLine();
                m = pattern.matcher(phone);
                if(m.matches()){
                    phone = getStandardPhone(phone);
                    phones = new HashSet<>();
                    phones.add(phone);
                    phoneList.put(surname.toUpperCase(), phones);
                }
                else{
                    System.out.println("Телефон введен неправильно");
                }
            }
            else{
                System.out.println("Фамилия указана неверно");
            }
        }
    }

    private String getStandardPhone(String phone){
        String result = "";
        int length = 0;
        char[] charArray = phone.toCharArray();
        for(char c: charArray){
            if(Character.isDigit(c)){
                length++;
                result+=c;
            }
        }
        if (length > 10){
            result = result.substring(length - 10);
        }
        return result;
    }
}
