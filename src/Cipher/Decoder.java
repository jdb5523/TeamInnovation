package Cipher;
/**
 *
 * @author Stephanie
 */
public class Decoder {

    public String decryptMessage(String informedcode) {
        String message, decryptedMessage = "";
        String allResults;
        String result;
        StringBuilder resultPool;
        Caesar caesarCipher;
        Atbash atbashCipher;
        Affine affineCipher;
        Baconian baconCipher;
        
        allResults = new String("");
        
        message = informedcode;
        caesarCipher = new Caesar();
        atbashCipher = new Atbash();
        affineCipher = new Affine();
        baconCipher = new Baconian();
        
        result = caesarCipher.decryptCaesar(message);
        resultPool = new StringBuilder(result);
        result = atbashCipher.decryptAtbash(informedcode);
        resultPool.append(result);
        result = affineCipher.decryptAffine(informedcode);
        resultPool.append(result);
        result = baconCipher.decryptBaconian(informedcode);
        resultPool.append(result);

        
        
        allResults = resultPool.toString();
        
        
        
    return allResults;
}}
