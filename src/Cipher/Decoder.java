package Cipher;

import java.util.ArrayList;

/**
 *
 * @author Stephanie
 */
public class Decoder {

    Caesar caesarCipher;
    Atbash atbashCipher;
    Affine affineCipher;
    Baconian baconianCipher;
    
    public Decoder() {
        
    }
    
    public ArrayList<String> affineDecrypt(String code) {
        affineCipher = new Affine();
        ArrayList<String> affineResults = affineCipher.decryptAffine(code);
        return affineResults;
    }
    
    public ArrayList<String> atbashDecrypt(String code) {
        atbashCipher = new Atbash();
        ArrayList<String> atbashResults = atbashCipher.decryptAtbash(code);
        return atbashResults;
    }
    
    public ArrayList<String> baconianDecrypt(String code) {
        baconianCipher = new Baconian();
        ArrayList<String> baconianResults = baconianCipher.decryptBaconian(code);
        return baconianResults;
    }
    
    public ArrayList<String> caesarDecrypt(String code) {
        caesarCipher = new Caesar();
        ArrayList<String> caesarResults = caesarCipher.decryptCaesar(code);
        return caesarResults;
    }
}
