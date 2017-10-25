package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DecryptViewController implements Initializable {

    AppController app;
    @FXML private TextField dateField;
    @FXML private TextField photogField;
    @FXML private TextField caseField;
    @FXML private TextArea inputArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUp(AppController app, String[] parameters, String fileContents) {
        this.app = app;
        dateField.setText(parameters[1]);
        photogField.setText(parameters[2]);
        caseField.setText(parameters[0]);
        inputArea.setText(fileContents);
    }
}
