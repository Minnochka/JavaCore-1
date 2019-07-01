package lesson6.Client;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lesson6.Server.AuthService;

import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    ToggleGroup style;

    @FXML
    RadioButton RadioBtnPink;

    @FXML
    RadioButton RadioBtnBlue;

    @FXML
    RadioButton RadioBtnGreen;

    @FXML
    RadioButton RadioBtnGrey;

    @FXML
    RadioButton RadioBtnYellow;

    @FXML
    RadioButton RadioBtnNone;

    @FXML
    HBox AuthPanel;

    @FXML
    HBox MessagePanel;

    @FXML
    HBox StylePanel;

    @FXML
    VBox test;

    @FXML
    TextField LoginField;

    @FXML
    TextField PasswordField;

    @FXML
    ComboBox UserList;

    private boolean isAuthorized;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8191;

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if(!isAuthorized){
            AuthPanel.setVisible(true);
            AuthPanel.setManaged(true);
            MessagePanel.setVisible(false);
            MessagePanel.setManaged(false);
            StylePanel.setVisible(false);
            StylePanel.setManaged(false);
        }
        else{
            AuthPanel.setVisible(false);
            AuthPanel.setManaged(false);
            MessagePanel.setVisible(true);
            MessagePanel.setManaged(true);
            StylePanel.setVisible(true);
            StylePanel.setManaged(true);
        }

    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObservableList<String> userList = FXCollections.observableArrayList();
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")){
                                setAuthorized(true);
                                str = str.substring(8);
                                System.out.println("str = " + str);
                                String[] toUsers = str.split("\n");
                                for(int i= 0; i < toUsers.length; i++){
                                    System.out.println("toUsers=" + toUsers[i]);
                                    //String[] infoUser = toUsers[i].split("/");
                                    //System.out.println(infoUser[0] + " " + infoUser[1]);
                                    userList.add(toUsers[i]);
                                }
                                break;
                            }
                            else{
                                textArea.appendText(str + "\n");
                            }
                        }
                        UserList.setItems(userList);
                        //UserList.setsetSelectedItem("Все");

                        while (true) {
                            String str = in.readUTF();
                            textArea.appendText(str);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setAuthorized(false);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void NewStyle() {
        if (RadioBtnPink.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style_pink.css");
        } else if (RadioBtnBlue.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style_blue.css");
        } else if (RadioBtnGreen.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style_green.css");
        } else if (RadioBtnGrey.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style_grey.css");
        } else if (RadioBtnYellow.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style_yellow.css");
        } else {
            test.getStylesheets().clear();
            test.getStylesheets().add("./lesson6/css/Style.css");
        }
    }

    public void sendMsg() {
        try {
            String toUser;
            System.out.println(textField.getText());
            //if(UserList.text
            if(UserList.getValue() == null){
                toUser = "Все";
            }
            else {
                toUser  = UserList.getValue().toString();
            }
            out.writeUTF(toUser + "\n" + textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(javafx.event.ActionEvent actionEvent) {
        if(socket == null || socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth " + LoginField.getText() + " " + PasswordField.getText());
            LoginField.clear();
            PasswordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
