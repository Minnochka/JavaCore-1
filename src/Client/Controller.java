package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller implements Initializable {
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
    VBox test;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            Date dateNow = new Date();
                            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            String dateStr = formatForDateNow.format(dateNow);
                            System.out.println(dateStr);
                            textArea.appendText("\n" + "User " + dateStr + "\n" + str + "\n");

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
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void NewStyle() {
        if (RadioBtnPink.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style_pink.css");
        } else if (RadioBtnBlue.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style_blue.css");
        } else if (RadioBtnGreen.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style_green.css");
        } else if (RadioBtnGrey.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style_grey.css");
        } else if (RadioBtnYellow.isSelected()) {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style_yellow.css");
        } else {
            test.getStylesheets().clear();
            test.getStylesheets().add("test/css/Style.css");
        }
    }

    public void sendMsg() {
        try {
            System.out.println(textField.getText());
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
