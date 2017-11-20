package Cipher;

import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

/**
 *
 * @author Stephanie
 */
public class GoogleTranslate {

    final String key = "AIzaSyCpmO6K7eRBBqqPA9emEYU4RzcTW5oDy-A";
    final String name = "translateproject";

    public String detectLanguage(String decodeText) {
        StringBuilder resultPool;
        System.out.println(decodeText);
        resultPool = new StringBuilder("");
        try {
            Translate t = new Translate.Builder(
                    com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
                    com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
                    .setApplicationName(this.name)
                    .build();
            Translate.Translations.List list = t.new Translations().list(
                    Arrays.asList(decodeText),
                    //Target language   
                    "EN");
            list.setKey(this.key);
            TranslationsListResponse response = list.execute();
            for (TranslationsResource tr : response.getTranslations()) {
                String lng = tr.getDetectedSourceLanguage();
                resultPool.append(lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultPool.toString();
    }

    public String translateLanguage(String decodeText, String detectLang) {
        StringBuilder resultPool;
        resultPool = new StringBuilder("");
        try {
            Translate t = new Translate.Builder(
                    com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
                    com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
                    .setApplicationName(this.name)
                    .build();
            Translate.Translations.List list = t.new Translations().list(
                    Arrays.asList(decodeText),
                    //Target language   
                    "EN");
            list.setKey(this.key);
            list.setSource(detectLang);
            TranslationsListResponse response = list.execute();
            for (TranslationsResource tr : response.getTranslations()) {
                resultPool.append(tr.getTranslatedText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultPool.toString();
    }

    public String checkEnglish(String word) {
        // load the dictionary into a set for fast lookups
        Set<String> dictionary = new HashSet<String>();
        String keyResult = new String("");
        word = word.toLowerCase();
        try {
            Scanner filescan = new Scanner(new File("dictionaries/englishDict.txt"));
            while (filescan.hasNextLine()) {
                dictionary.add(filescan.nextLine().toLowerCase());
            }
            // start the search, pass empty stack to represent words found so far
            keyResult = search(word, dictionary);
        } catch (FileNotFoundException ex) {
        }
        return keyResult;
    }

    public String search(String input, Set<String> dictionary) {
        String result = new String("");
        String check = input;
        if (dictionary.contains(check)) {
            result = "WORD FOUND";
        } else {
            // there's more input left, search the remaining part
            result = "NO WORD FOUND";
        }

        return result;
    }

    public String runProcess(String informedLanguage, String decryptedMessage, String informedName) {
        int key, k;
        double ratio, ratTotal, length;
        String check= "";
        String transMessage = ""; 
        String finalTrans = "";
        String finalResult = "";
        StringBuilder resultPool = new StringBuilder("");

        String language = informedLanguage;
        String langName = informedName;
        k = 0;
        ratTotal = 0;

        DecimalFormat twoDForm = new DecimalFormat("#.00");

        if ("English".equals(langName)) {

            String[] splitMessage = decryptedMessage.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            length = splitMessage.length;

            for (int i = 0; i < splitMessage.length; i++) {

                check = checkEnglish(splitMessage[i]);

                if ("WORD FOUND".equals(check)) {
                    ratTotal++;
                }

                resultPool.append(transMessage + " ");
                k++;
            }

            ratio = ratTotal / length;

            if (ratio == 0.7) {
                resultPool.append("Ratio: " + twoDForm.format(ratio));
                finalResult = resultPool.toString();
            } else if (ratio > 0.7) {
                resultPool.append("Ratio: " + twoDForm.format(ratio));
                finalResult = resultPool.toString();
            } else if (ratio < 0.7) {
                finalResult = ("LOW RATIO");
            }
        } else {
            finalTrans = translateLanguage(decryptedMessage, language);

            String[] splitMessage = finalTrans.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            length = splitMessage.length;

            for (int i = 0; i < splitMessage.length; i++) {

                check = checkEnglish(splitMessage[i]);

                if ("WORD FOUND".equals(check)) {
                    ratTotal++;
                }
            }
            ratio = ratTotal / length;

            if (ratio == 0.7) {
                resultPool.append("\n Translated Message: " + finalTrans + "\n Ratio: " + twoDForm.format(ratio));
                finalResult = resultPool.toString();
            } else if (ratio > 0.7) {
                resultPool.append("\n Translated Message: " + finalTrans + "\n Ratio: " + twoDForm.format(ratio));
                finalResult = resultPool.toString();
            } else {
                finalResult = ("LOW RATIO");
            }

        }
        return finalResult;
    }

}
