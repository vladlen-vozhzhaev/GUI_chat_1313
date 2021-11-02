package com.example.gui_chat_1313;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HelloController {
    DataOutputStream out;
    @FXML
    Button connectBtn;
    @FXML
    Button sendBtn;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;
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
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String response = in.readUTF();
                            textArea.appendText(response+"\n");
                        }
                    } catch (IOException e) {
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