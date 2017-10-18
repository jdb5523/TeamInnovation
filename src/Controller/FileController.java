package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileController {

    AppController app;
    private String fileContents = "";
    private File file;
    FileChooser textFileChooser = new FileChooser();
    FileChooser imageFileChooser = new FileChooser();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Default constructor for FileController
     * @param app Takes in AppController as argument for two-way communication
     */
    public FileController(AppController app) {
        this.app = app;
    }
    /**
     * After selecting a .txt file, text within that file is stored within the 
     * fileContents String object
     * @throws FileNotFoundException Throws error if the file is not found
     * @throws IOException Throws error if the file is unable to be read
     */
    public void importText() throws FileNotFoundException, IOException {
        textFileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        file = textFileChooser.showOpenDialog(app.getStage());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text = "";
            while ((text = reader.readLine()) != null) {
                fileContents += text;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getPath() {
        imageFileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("JPEG Images", "*.jpg"));
        file = imageFileChooser.showOpenDialog(app.getStage());
        return file.getAbsolutePath();
    }
    /**
     * Allows user to select an image file to be processed by tesseract. After
     * image file is selected, .txt file is generated on the desktop.
     * @param parameters Takes in the parameters for the image file to be OCR'd
     * @throws IOException Throws error if file is unable to be found or read
     * @throws java.sql.SQLException Throws error if the DatabaseController fails
     * to create record in Ocr table
     */
    public void ocr(String[] parameters) throws IOException, SQLException {
        if (!parameters[0].isEmpty()) { 
            app.getDb().imageEntry(Integer.parseInt(parameters[0]), parameters[1], 
                Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), parameters[4]);
        } else {
            app.getDb().imageEntry(parameters[1], Integer.parseInt(parameters[2]), 
                    Integer.parseInt(parameters[3]), parameters[4]);
        }
        /*String fileName = "C:\\Users\\Jared\\Desktop\\OCR_" + app.getDb().getNextOcrId();
        String command = "tesseract " + file.getAbsolutePath() + " " + fileName;
        Process process = Runtime.getRuntime().exec(command);
        app.getDb().ocrEntry(Integer.parseInt(parameters[0]), parameters[1],  
                Integer.parseInt(parameters[2]),  Integer.parseInt(parameters[3]),  
                parameters[4],  parameters[5]);
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
            File textFile = new File(fileName + ".txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
                String text = "";
                while ((text = reader.readLine()) != null) {
                    System.out.println(text);
                    fileContents += text;
                }
                app.getMain().getInputArea().setText(fileContents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    Timer timer = new Timer("Timer");
    long delay = 3000L;
    timer.schedule(task, delay);*/
    }
     
    public void saveOutput() throws IOException {
        File outputFile = textFileChooser.showSaveDialog(app.getStage());
        try {
            FileWriter writer = new FileWriter(outputFile); 
            writer.write(app.getMain().getOutputText());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }           
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }
}
