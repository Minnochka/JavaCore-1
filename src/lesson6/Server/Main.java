package lesson6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    private Vector<ClientHandler> clients;

    public Main() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connection();
            //String str =  AuthService.getUsernameByLoginPass("vvvv", "4444");
            //System.out.println(str);
            server = new ServerSocket(8191);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnection();
        }
    }

    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client){
        clients.remove(client);
    }

    public void broadCastMsg(String msg, String toUser, String fromUser) {
        if (toUser.equals("Все")) {
            for (ClientHandler o : clients) {
                o.sendMsg(msg);
            }
        }
        else {
            for (ClientHandler o : clients) {
                System.out.println("toUser" + o.getUsername() + "-" + toUser);
                if (o.getUsername().equals(toUser) || o.getUsername().equals(fromUser)) {
                    System.out.println("toUser22" + o.getUsername());
                    o.sendMsg(msg);
                }
            }
        }
    }
}
