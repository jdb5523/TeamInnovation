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
    String message, decryptedMessage, finalResult, language, langName;
    GoogleTranslate trans;
    ArrayList<String> allResults;

    public ArrayList<String> decryptAtbash(String informedcode) {
        message = informedcode;
        decryptedMessage = "";
        finalResult = "";
        language = "";
        langName = "";
        result = new StringBuilder();
        trans = new GoogleTranslate();
        allResults = new ArrayList();

        for (int i = 0; i < message.length(); ++i) {
            character = message.charAt(i);
            if (character >= 'a' && character <= 'z') {
                character = (char) ('z' - character);
                character = (char) (character + 'a');
            } else {
                character = (char) ('Z' - character);
                character = (char) (character + 'A');
            }
            decryptedMessage += character;
        }
        /*language = trans.detectLanguage(decryptedMessage);

        Locale loc = new Locale(language);
        langName = loc.getDisplayLanguage(loc);

        finalResult = trans.runProcess(language, decryptedMessage, langName);

        if ("LOW RATIO".equals(finalResult)) {
            allResults.add("");
        } else {
            allResults.add("Atbash Cipher " + ":\n" + "Decrypted Message: " + decryptedMessage + "\n"
                    + "Language: " + langName + "\n" + " - FINAL RESULT: " + finalResult + "\n" + "\n" + "\n");
        }*/
        allResults.add(decryptedMessage);
        return allResults;
    }
}
