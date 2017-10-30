package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DetailViewController implements Initializable {

    AppController app;
    @FXML private Button closeButton;
    @FXML private ImageView helpIcon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
    
    @FXML
    protected void handleHelpButtonAction() {
        try {
            app.showUserGuide(7);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) {
        Boolean isEnter = key.getCode() == KeyCode.ENTER;
        if (isEnter && closeButton.isFocused()) {
            handleCloseButtonAction();
        } 
    }
    @FXML
    protected void handleCloseButtonAction() {
        app.closeDetail();
    }
    public void setUp(AppController app) {
        this.app = app;
    }
}
