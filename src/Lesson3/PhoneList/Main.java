package Lesson3.PhoneList;

public class Main {
    public static void main(String[] args){
        PhoneList phoneList = new PhoneList();
        phoneList.getInfo();
        boolean close = false;
        /*while(!close){
            System.out.println("---------------------------\n" +
                    "1. Добавить нового человека/телефон.\n" +
                    "2. Найти список телефонов по фамилии.\n" +
                    "3. Посмотреть данные телефонного справочника\n" +
                    "4. Выход.");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice){
                case 1:{
                    phoneList.add();
                    break;
                }
                case 2:{
                    phoneList.get();
                    break;
                }
                case 3:{
                    phoneList.getInfo();
                    break;
                }
                default:{
                    close = true;
                    break;
                }
            }
        }*/

        System.out.println("--------------------\nДобавить нового человека/телефон");
        phoneList.add("k", "(905)876-73-41");
        phoneList.getInfo();
        phoneList.add("маккаферри", "(905)876-73-41");
        phoneList.getInfo();
        phoneList.add("МаккаФеррИ", "8(905)876-7341");
        phoneList.getInfo();
        phoneList.add("маккаферри", "905-876-73-43");
        phoneList.getInfo();
        System.out.println("--------------------\nНайти список телефонов по фамилии");
        phoneList.get("сальман");
        phoneList.get("апраксин");
        phoneList.get("коханов");
        phoneList.get("кохонов");
    }
}
