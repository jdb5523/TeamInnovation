package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DetailViewController implements Initializable {

    AppController app;
    @FXML private Button closeButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
