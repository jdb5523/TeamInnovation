/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cipher;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Stephanie
 */
public class Baconian {

    int spaceStart = 0, spaceEnd = 0, cInWord;
    String message, concatMessage;
    StringBuilder decryptChars;
    String decryptedMessage;
    char character, decryptCharacter;
    int characterCount[], wordAmt;
    String result, processW[], processed;
    ArrayList<String> allResults;
    String words[], ltrcode[], digcode[];

    public ArrayList<String> decryptBaconian(String informedcode) {

        message = informedcode.toLowerCase();
        words = message.split("\\s");
        wordAmt = words.length;
        characterCount = new int[wordAmt];
        processW = new String[wordAmt];
        concatMessage = message.replaceAll("\\W", "");
        decryptedMessage = "";
        decryptChars = new StringBuilder();
        result = "";
        allResults = new ArrayList();

        ltrcode = new String[]{"aaaaa", "aaaab", "aaaba", "aaabb", "aabaa", "aabab", "aabba",
            "aabbb", "abaaa", "abaab", "ababa", "ababb", "abbaa", "abbab", "abbba", "abbbb",
            "baaaa", "baaab", "baaba", "baabb", "babaa", "babab", "babba", "babbb", "bbaaa",
            "bbaab"};
        digcode = new String[]{"00000", "00001", "00010", "00011", "00100", "00101", "00110",
            "00111", "01000", "01001", "01010", "01011", "01100", "01101", "01110", "01111",
            "10000", "10001", "10010", "10011", "10100", "10101", "10110", "10111", "11000",
            "11001"};

        if (concatMessage.length() % 5 == 0) {
            for (int a = 0; a < wordAmt; a++) {
                characterCount[a] = words[a].length() / 5;
            }
            int k = 0;
            int interval = 5;
            int arrayLength = (int) Math.ceil(((concatMessage.length() / (double) interval)));
            String[] characters = new String[arrayLength];
            
            for (int j = 0; j < characters.length; j++) {
                characters[j] = concatMessage.substring(k, k + interval);
                k += interval;
                String curChar = characters[j];
                for (int n = 0; n < ltrcode.length; n++) {
                    if (ltrcode[n].equals(curChar)) {
                        decryptCharacter = (char) (n + 'a');
                        decryptChars.append(decryptCharacter);
                    } else if (digcode[n].equals(curChar)) {
                        decryptCharacter = (char) (n + 'a');
                        decryptChars.append(decryptCharacter);
                    } else {
                        decryptChars.append("");
                    }
                }
            }
            processed = decryptChars.toString();
            for (int q = 0; q < this.wordAmt; q++) {
                cInWord = characterCount[q];
                spaceStart = spaceEnd;
                spaceEnd = spaceStart + cInWord;
                processW[q] = processed.substring(spaceStart, spaceEnd) + " ";
                decryptedMessage += processW[q];
            }
            System.out.println(decryptedMessage);
            allResults.add(decryptedMessage);
        } else {
            allResults.add("Not encrypted with Baconian cipher: message "
                    + "is not a multiple of five");
        }
        return allResults;
    }
}
