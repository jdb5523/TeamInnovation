package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AboutViewController implements Initializable {

    AppController app;
    @FXML private ImageView logo;
    @FXML private Button closeButton;
    @FXML private TextArea aboutArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadText();
        try {
            Image image = new Image(new FileInputStream("images/logo - About.jpg"));
            logo.setImage(image);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }    
    @FXML
    protected void handleKeyPressed(KeyEvent key) throws IOException {
        if (key.getCode() == KeyCode.ENTER && closeButton.isFocused()) {
            handleCloseButtonAction();
        }
    }
    
    @FXML
    protected void handleCloseButtonAction() {
        app.closeAbout();
    }
    
    public void setUp(AppController app) {
        this.app = app;
    }
    
    public void loadText() {
        aboutArea.setText("Nautilus Decryption Application ver. 0.1a"
                + "\nDeveloped by Team Innovation (2017)"
                + "\n\nUtilizes Tesseract OCR which is licensed under the Apache License, "
                + "Version 2.0\nhttp://www.apache.org/licenses/LICENSE-2.0"
                + "\nRepository: https://github.com/tesseract-ocr/tesseract");
    }
}
