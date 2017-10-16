/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GuideText;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GuideViewController implements Initializable {
    
    GuideText instance = GuideText.getInstance();
    AppController app;
    ArrayList<String> items;
    @FXML private ListView<String> helpItems;
    @FXML private TextArea helpText;
    @FXML private Button closeButton;
    @FXML ImageView logo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/logo - About.jpg"));
            logo.setImage(image);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        items = new ArrayList();
        addItems();
        helpItems.getItems().addAll(items);
        helpItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        helpText.setText(instance.getIntro());
    }    
    @FXML protected void handleItemSelection() {
        String selectedItem = helpItems.getSelectionModel().getSelectedItem();
        switch (selectedItem) {
            case "Introduction": helpText.setText(instance.getIntro());
            break;
            case "Login Screen": helpText.setText(instance.getLogin());
            break;
            case "Resetting Password": helpText.setText(instance.getReset());
            break;
            case "Main Screen": helpText.setText(instance.getMain());
            break;
            case "OCR Process": helpText.setText(instance.getOcr());
            break;
            case "Importing Text": helpText.setText(instance.getImporting());
            break;
            case "Decrypting Text": helpText.setText(instance.getDecrypt());
            break;
            case "Saving Results": helpText.setText(instance.getSave());
            break;
        }
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) throws IOException {
        if (key.getCode() == KeyCode.ENTER && closeButton.isFocused()) {
            handleCloseButtonAction();
        } else if ((key.getCode() == KeyCode.UP) || (key.getCode() == KeyCode.DOWN)) {
            key.consume();
        }
    }
    @FXML
    public void handleCloseButtonAction() {
        app.closeGuide();
    }
    
    public void setUp (AppController app) {
        this.app = app;
    }
    
    public void showIntroduction() {
        helpText.setText("INTRODUCTION BLAH BLAH BLAH");
    }
    
    public void addItems() {
        items.add("Introduction");
        items.add("Login Screen");
        items.add("Resetting Password");
        items.add("Main Screen");
        items.add("OCR Process");
        items.add("Importing Text");
        items.add("Decrypting Text");
        items.add("Saving Results");
    }
}
