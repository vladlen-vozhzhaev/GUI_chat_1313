package com.example.gui_chat_1313;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {
    DataOutputStream out;
    ArrayList<String> usersName = new ArrayList<>();
    @FXML
    Button connectBtn;
    @FXML
    Button sendBtn;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;
    @FXML
    TextArea onlineUsersTextArea;
    @FXML
    public void handlerSend(){
        String text = textField.getText();
        textArea.appendText(text+"\n");
        textField.clear();
        textField.requestFocus();
        try {
            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void connect(){
        try {
            Socket socket = new Socket("127.0.0.1", 8178);
            System.out.println("Успешно подключен");
            out = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String response;
                            Object object = ois.readObject();
                            System.out.println(object.getClass());
                            if(object.getClass().equals(usersName.getClass())){
                                usersName = (ArrayList<String>) object;
                                System.out.println(usersName);
                                onlineUsersTextArea.clear();
                                for (String userName:usersName) {
                                    onlineUsersTextArea.appendText(userName+"\n");
                                }
                            }else{
                                response = object.toString();
                                textArea.appendText(response+"\n");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
            sendBtn.setDisable(false);
            connectBtn.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}