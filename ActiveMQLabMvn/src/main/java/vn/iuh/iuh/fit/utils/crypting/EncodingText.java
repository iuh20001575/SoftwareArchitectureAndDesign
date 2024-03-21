package vn.iuh.iuh.fit.utils.crypting;

public interface EncodingText {
    final String CRYPT_ALGORITHM = "AES";
    final String DEFAULT_PASSWORD = "S3cr3t";

    String encrypt(String plainText);
    String decrypt(String encryptedText);
}
