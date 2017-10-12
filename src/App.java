
import Controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppController app = new AppController(stage);
    }
    
    public static void main(String[] args) {
        System.out.println("Connecting to database...");
        launch(args);      
    }
    
}
