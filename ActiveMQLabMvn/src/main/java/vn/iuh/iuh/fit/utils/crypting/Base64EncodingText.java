package vn.iuh.iuh.fit.utils.crypting;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64EncodingText implements EncodingText {
    @Override
    public String encrypt(String plainText) {
        return Base64.encodeBase64String(plainText.getBytes());
    }

    @Override
    public String decrypt(String encryptedText) {
        return new String(Base64.decodeBase64(encryptedText));
    }
}
