package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OptionViewController implements Initializable {

    AppController app;
    @FXML private Button processButton;
    @FXML private Button historyButton;
    @FXML private ImageView helpIcon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }    
    
    @FXML
    protected void handleProcessButtonAction() throws IOException {
        app.showImageSelection();
    }
    
    @FXML 
    protected void handleHistoryButtonAction() throws IOException, SQLException {
        app.showHistory();
    }
    
    @FXML
    protected void handleHelpButtonAction() {
        try {
            app.showUserGuide(3);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleKeyPressed(KeyEvent key) throws IOException, SQLException {
        if (key.getCode() == KeyCode.ENTER && processButton.isFocused()) {
            handleProcessButtonAction();
        } else if (key.getCode() == KeyCode.ENTER && historyButton.isFocused()) {
            handleHistoryButtonAction();
        }
    }
    
    public void setUp(AppController app) {
        this.app = app;
    }
}
