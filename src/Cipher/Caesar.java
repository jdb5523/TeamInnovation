package Cipher;

import java.util.ArrayList;
import java.util.Locale;

public class Caesar {

    String message, decryptedMessage = "", finalResult, loopInfo;
    int key;
    char character;
    GoogleTranslate trans;
    String language;
    ArrayList<String> allResults;

    public ArrayList<String> decryptCaesar(String informedcode) {

        message = informedcode;
        allResults = new ArrayList();
        trans = new GoogleTranslate();
        language = "";
        String langName = "";
        finalResult = "";

        for (key = 1; key < 27; ++key) {
            decryptedMessage = "";
            for (int i = 0; i < message.length(); ++i) {
                character = message.charAt(i);
                if (character >= 'a' && character <= 'z') {
                    character = (char) (character - key);
                    if (character < 'a') {
                        character = (char) (character + 'z' - 'a' + 1);
                    }
                    decryptedMessage += character;
                } else if (character >= 'A' && character <= 'Z') {
                    character = (char) (character - key);
                    if (character < 'A') {
                        character = (char) (character + 'Z' - 'A' + 1);
                    }
                    decryptedMessage += character;
                } else {
                    decryptedMessage += character;
                }
            }
            allResults.add(decryptedMessage);
            /*if ("LOW RATIO".equals(finalResult)) {
                allResults.add("Key " + key + ":\nNo language recognized");
            } else {
                allResults.add("Key " + key + ":\n" + "Decrypted Message: " + decryptedMessage + "\n"
                        + "Language: " + langName);
            }*/
        }
        return allResults;
    }

}
