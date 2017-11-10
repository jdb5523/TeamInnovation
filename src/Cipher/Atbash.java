package Cipher;

import java.util.*;
import javax.swing.*;

/**
 *
 * @author Stephanie
 */
public class Atbash {

    char character, decryptCharacter;
    StringBuilder result;
    String message, decryptedMessage, finalResult, language, langName;
    GoogleTranslate trans;
    JTextArea allResults;

    public String decryptAtbash(String informedcode) {
        message = informedcode;
        decryptedMessage = new String("");
        finalResult = new String("");
        language = new String("");
        langName = new String("");
        result = new StringBuilder();
        trans = new GoogleTranslate();
        allResults = new JTextArea("");

        for (int i = 0; i < message.length(); ++i) {
            character = message.charAt(i);
            character = (char) ('z' - character);
            character = (char) (character + 'a');
            decryptedMessage += character;
        }
        language = trans.detectLanguage(decryptedMessage);

        Locale loc = new Locale(language);
        langName = loc.getDisplayLanguage(loc);

        finalResult = trans.runProcess(language, decryptedMessage, langName);

        if ("LOW RATIO".equals(finalResult)) {
            allResults.append("");
        } else {
            allResults.append("Atbash Cipher " + ":\n" + "Decrypted Message: " + decryptedMessage + "\n"
                    + "Language: " + langName + "\n" + " - FINAL RESULT: " + finalResult + "\n" + "\n" + "\n");
        }

        return allResults.getText();
    }
}