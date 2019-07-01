package lesson6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Main server;
    private String username;
    private int id;


    public ClientHandler(Socket socket, Main server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                System.out.println(tokens[1] + "-" + tokens[2]);
                                String nick = AuthService.getUsernameByLoginPass(tokens[1], tokens[2]);
                                int new_id = AuthService.userAuthorized(tokens[1], tokens[2]);
                                if(nick != null && new_id == 1){
                                    //sendMsg("/authok");
                                    username = nick;
                                    id = new_id;
                                    server.subscribe(ClientHandler.this);

                                    HashMap chatList = AuthService.chatList(username);
                                    Set<HashMap.Entry<Integer,String>> set = chatList.entrySet();
                                    StringBuffer userList = new StringBuffer("/authok");
                                    for (HashMap.Entry<Integer,String> sg : set) {
                                        userList.append("\n" + chatList.get(sg.getKey()));
                                    }
                                    sendMsg(userList.toString());
                                    break;
                                }
                                else if (new_id != 1){
                                    sendMsg("Вход уже выполнен!");
                                }
                                else{
                                    sendMsg("Неверный логин/пароль");
                                }
                            }
                            server.broadCastMsg(str, username, username);
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                break;
                            }
                            Date dateNow = new Date();
                            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            String dateStr = formatForDateNow.format(dateNow);
                            System.out.println(str);
                            server.broadCastMsg("\n" + ClientHandler.this.username + " " + dateStr + "\n" + str.substring(str.indexOf("\n") + 1) + "\n", str.substring(0, str.indexOf("\n")), username);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        AuthService.userClosed(username);
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

}
