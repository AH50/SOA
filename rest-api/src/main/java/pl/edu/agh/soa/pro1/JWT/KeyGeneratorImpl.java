package pl.edu.agh.soa.pro1.JWT;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;


public class KeyGeneratorImpl implements KeyGenerator {

    @Override
    public Key generateKey() {
        String keyString = "4mYW9Rhfy7BaId1n8ZKxyojzlyyhvgIi";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}