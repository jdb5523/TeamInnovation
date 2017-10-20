package Controller;

import Model.AboutText;
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
    AboutText instance = AboutText.getInstance();
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
        aboutArea.setText(instance.getAbout());
    }
}
