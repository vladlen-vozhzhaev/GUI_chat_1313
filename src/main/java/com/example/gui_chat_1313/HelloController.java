package com.example.gui_chat_1313;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
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
    }
    @FXML
    public void connect(){

    }
}