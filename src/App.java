
import Controller.AppController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppController app = new AppController(stage);
        stage.setOnCloseRequest((new EventHandler<WindowEvent>(){
        @Override
        public void handle(WindowEvent arg0) {
                            arg0.consume();
            Platform.exit();
        }
    }));
    }
    
    public static void main(String[] args) {
        System.out.println("Connecting to database...");
        launch(args);      
    }
    
}
