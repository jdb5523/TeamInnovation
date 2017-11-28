package Cipher;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Stephanie
 */
public class Atbash {

    char character, decryptCharacter;
    StringBuilder result;
    String message, decryptedMessage;
    ArrayList<String> allResults;

    public ArrayList<String> decryptAtbash(String informedcode) {
        message = informedcode;
        decryptedMessage = "";
        result = new StringBuilder();
        allResults = new ArrayList();

        for (int i = 0; i < message.length(); ++i) {
            character = message.charAt(i);
            if (character >= 'a' && character <= 'z') {
                character = (char) ('z' - character);
                character = (char) (character + 'a');
            } else if (character >= 'A' && character <= 'Z') {
                character = (char) ('Z' - character);
                character = (char) (character + 'A');
            } else {
                character = (char) " ".charAt(0);
            }
            decryptedMessage += character;
        }
        allResults.add(decryptedMessage);
        return allResults;
    }
}
