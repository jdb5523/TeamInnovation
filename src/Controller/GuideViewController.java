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
    int selectedItem = 0;
    
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
    protected void handleItemSelection() {
        switch (selectedItem) {
            case 0: helpText.setText(instance.getIntro());
            break;
            case 1: helpText.setText(instance.getLogin());
            break;
            case 2: helpText.setText(instance.getReset());
            break;
            case 3: helpText.setText(instance.getMain());
            break;
            case 4: helpText.setText(instance.getOcr());
            break;
            case 5: helpText.setText(instance.getImporting());
            break;
            case 6: helpText.setText(instance.getDecrypt());
            break;
            case 7: helpText.setText(instance.getSave());
            break;
        }
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) throws IOException {
        KeyCode code = key.getCode();
        if (code == KeyCode.ENTER && closeButton.isFocused()) {
            handleCloseButtonAction();
        } else if (code == KeyCode.DOWN) {
            if (selectedItem < 7) {
                selectedItem += 1;
            }
            handleItemSelection();
        } else if (code == KeyCode.UP) {
            if (selectedItem > 0) {
                selectedItem -= 1;
            }
            handleItemSelection();
        }
    }
    
    @FXML 
    protected void handleMouseClicked() {
        selectedItem = helpItems.getSelectionModel().getSelectedIndex();
        handleItemSelection();
    }
    @FXML
    protected void handleCloseButtonAction() {
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
