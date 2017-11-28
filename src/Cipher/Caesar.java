package Cipher;

import java.util.ArrayList;
import java.util.Locale;

public class Caesar {

    String message, decryptedMessage = "";
    int key;
    char character;
    ArrayList<String> allResults;

    public ArrayList<String> decryptCaesar(String informedcode) {

        message = informedcode;
        allResults = new ArrayList();
        String langName = "";
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
        }
        return allResults;
    }

}
