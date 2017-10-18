package Controller;

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
    private final String url = "jdbc:sqlserver://DESKTOP-C3ACMOB;"
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
     * @param processor
     * @param filePath
     * @throws java.sql.SQLException Throws exception if the SQL INSERT
     * statement fails
     */
    public void imageEntry(int caseId, String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CASE_ID, CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES (" + caseId + ", "
                + captureDate + ", " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
    }
    
    public void imageEntry(String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES (" + captureDate + ", " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
    }

    /**
     * Creates OCR entry in SQL Server database
     *
     * @param path The file pathway to the resultant text file of the OCR
     * process
     * @param date The date on which the OCR was completed
     * @param userID The user_id of whom initiated the OCR process
     * @throws SQLException Throws exception if the SQL INSERT statement fails
     */
    public void ocrEntry(String path, String date, int userID) throws SQLException {
        sql = "INSERT INTO Ocr (file_path, ocr_timestamp, user_id) "
                + "VALUES ('" + path + "', "
                + "'" + date + "'" + ", " + userID + ")";
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
        sql = "SELECT ocr_id FROM OCR";
        ResultSet ocrID = state.executeQuery(sql);
        while (ocrID.last()) {
            return ocrID.getInt("ocr_id") + 1;
        }
        return 0;
    }
    
    public void lockAccount(String username) throws SQLException {
        Date date = new Date();
        sql = "UPDATE [USER] SET LOCKED=1, LOCKOUT_DATE ='" + df.format(date) 
                + "'" + " WHERE USER_NAME='" + username + "'";
        state.executeUpdate(sql);
        app.getLoginView().setLockedFlag(1);
    }

}
