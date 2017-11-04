package Controller;

import Model.Image;
import Model.ImageList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseController {

    private AppController app;
    private final String url = "jdbc:sqlserver://DESKTOP-TF4DGV3;"
            + "integratedSecurity=true;databaseName=Nautilus";
    private String sql = "";
    ResultSet result;
    Statement state;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Default constructor for DatabaseController
     *
     * @param app Takes in AppController object as an argument for two-way
     * communication
     */
    public DatabaseController(AppController app) {
        this.app = app;
    }

    /**
     * Attempts to connect to the database defined in the String object: url
     */
    public void connect() {
        try {
            Connection connect = DriverManager.getConnection(url);
            System.out.println("Connected to database");
            state = connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Handles authentication for logging in
     *
     * @param user Username of the person attempting to login
     * @param password Password being tested against the password stored in the
     * database
     * @return Returns a ResultSet object containing the user data
     * @throws java.sql.SQLException If SQL query is invalid
     */
    public ResultSet getUser(String user, String password) throws SQLException {
        sql = "SELECT * FROM [USER] WHERE USER_NAME = '" + user + "'";
        result = state.executeQuery(sql);
        return result;
    }
    
    public int getUserId(String user) throws SQLException {
        sql = "SELECT USER_ID FROM [USER] WHERE USER_NAME='" + user + "'";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt("USER_ID");
        }
        return 0;
    }
    /**
     * This method increments the user's LOGIN_ATTEMPT's field by 1 whenever an
     * incorrect password is submitted.  If the counter is already at 3, the LOCKED 
     * flag is set to true, instead.
     * @param user Username of the account whose attempts is being incremented
     * @throws SQLException 
     */
    public void addLoginAttempt(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=LOGIN_ATTEMPT+1 "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);     
    }
    
    public void resetLoginAttempts(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=0 "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);
    }
    
    public void removeLock(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=0,LOCKED=0, LOCKOUT_DATE=null "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);
    }
    /**
     * Performs the validation for the password reset page
     * @param username The username of the user attempting to reset password
     * @param userID The user_id of the user attempting to reset password
     * @return Returns true if the username/user_id combination is found in DB
     * @throws SQLException Throws error if SQL query is invalid
     */
    public Boolean isUserValid(String username, int userID) throws SQLException {
        Boolean isComboValid = false;
        sql = "SELECT * FROM [USER] WHERE USER_ID=" + userID;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            if (result.getString("USER_NAME").equals(username)) {
                isComboValid = true;
            } 
        }
        return isComboValid;
    }
    /**
     * Method for obtaining the challenge question associated with the User entity
     * @param id The user_id of the user for whom the question is being requested
     * @return Returns a String object of the challenge question
     * @throws SQLException Throws error if SQL query is invalid
     */
    public String getChallengeQuestion(int id) throws SQLException {
        String question = "";
        sql = "SELECT CHALLENGE_QUESTION FROM [USER] WHERE"
                + " USER_ID=" + id;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            question = result.getString("CHALLENGE_QUESTION");
        }
        return question;
    }
    /**
     * Method for obtaining the corresponding answer to the challenge question
     * @param id The user_id of the user whose challenge response is being requested
     * @return Returns a String object of the valid challenge response
     * @throws SQLException Throws error if SQL query is invalid
     */
    public String getChallengeResponse(int id) throws SQLException {
        String response = "";
        sql = "SELECT CHALLENGE_ANSWER FROM [USER] WHERE USER_ID=" + id;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            response = result.getString("CHALLENGE_ANSWER");
            System.out.println(response);
        }
        return response;
    }
    /**
     * Updates the password for a user
     * @param password The new password to be used
     * @param id The user_id of the user whose password is being updated
     * @throws SQLException Throwss error is SQL update is invalid
     */
    public void setNewPassword(String password, int id) throws SQLException {
        sql = "UPDATE [USER] SET PASSWORD='" + password + "'" +" WHERE USER_ID="
                + id;
        state.executeUpdate(sql);
    }
    
    /**
     * Creates entry in SQL Server database
     *
     * @param caseId
     * @param captureDate
     * @param photographer
     * @param processor
     * @param filePath
     * @return 
     * @throws java.sql.SQLException Throws exception if the SQL INSERT
     * statement fails
     */
    public int imageEntry(int caseId, String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CASE_ID, CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES (" + caseId + ", '"
                + captureDate + "', " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
        sql = "SELECT MAX(IMAGE_ID) FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    
    public int imageEntry(String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES ('" + captureDate + "', " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
        sql = "SELECT MAX(IMAGE_ID) FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    
    public ImageList createImageList() throws SQLException {
        ImageList images = new ImageList();
        sql = "SELECT * FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            Image image = new Image(result.getInt(1), result.getInt(2), 
                    result.getString(3), result.getInt(4), result.getInt(5), 
                    result.getString(6), result.getString(7));
            images.addImage(image);
        }
        return images;
    }

    /**
     * Creates OCR entry in SQL Server database
     *
     * @param imageId
     * @param result
     * @param date The date on which the OCR was completed
     * @param processor
     * @param error
     * @throws SQLException Throws exception if the SQL INSERT statement fails
     */
    public void ocrEntry(int imageId, String result, String date, 
            int processor, int error) throws SQLException {
        sql = "INSERT INTO OCR (IMAGE_ID, OCR_RESULT, OCR_DATE, PROCESSED_BY, OCR_ERROR) "
                + "VALUES (" + imageId + ", '" + result + "', '" + date + "', " + processor + ", " + error + ")";
        state.executeUpdate(sql);
    }
    
    public void ocrEntry(int caseId, String captureDate, int photographer, 
            int processor, String filePath, String additionalNotes) throws SQLException {
        sql = "INSERT INTO OCR (CASE_ID, CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH, ADDITIONAL_NOTES) "
                + "VALUES ('" + caseId + "', "
                + "'" + captureDate + "'" + ", " + photographer + ", " + processor
                + ", " + "'" + filePath + "', '" + additionalNotes + "'\")";
        state.executeUpdate(sql);
    }
    
    public int getNextOcrId() throws SQLException {
        sql = "SELECT OCR_ID FROM OCR";
        ResultSet ocrID = state.executeQuery(sql);
        while (ocrID.next()) {
            return ocrID.getInt("ocr_id") + 1;
        }
        return 0;
    }
    
    public String[] getRecordDetails(int imageId) throws SQLException {
        String[] results = new String[6];
        sql = "SELECT Image.CAPTURE_DATE, Decrypt.DECRYPT_DATE, Cipher.CIPHER_NAME, " +
                "Decrypt.RATING, OCR.OCR_RESULT, Image.ADDITIONAL_NOTES " 
                + "FROM Image JOIN OCR ON Image.IMAGE_ID = OCR.IMAGE_ID "
                + "JOIN DECRYPT ON OCR.OCR_ID = DECRYPT.OCR_ID " 
                + "JOIN CIPHER ON DECRYPT.CIPHER = CIPHER.CIPHER_ID WHERE "
                + "Image.IMAGE_ID = " + imageId;
        System.out.println(sql);
        result = state.executeQuery(sql);
        while (result.next()) {
            results[0] = result.getString(1);
            results[1] = result.getString(2);
            results[2] = result.getString(3);
            results[3] = result.getString(4);
            results[4] = result.getString(5);
            results[5] = result.getString(6);
            System.out.println(results[0]);
            System.out.println(results[1]);
            System.out.println(results[2]);
            System.out.println(results[3]);
            System.out.println(results[4]);
            System.out.println(results[5]);
            
        }
        return results;
    }
    
    public void lockAccount(String username) throws SQLException {
        Date date = new Date();
        sql = "UPDATE [USER] SET LOCKED=1, LOCKOUT_DATE ='" + df.format(date) 
                + "'" + " WHERE USER_NAME='" + username + "'";
        state.executeUpdate(sql);
        app.getLoginView().setLockedFlag(1);
    }

}
