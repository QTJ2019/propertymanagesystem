package com.jian.propertymanagesystem.util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/2/23 20:44
 * @Version
 */
public class RSAEncryptUtil {
    public static String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIiVEXKFYF+/0OAMcvHTiQBGDo7mRiafYpM0wKpmoFUHs3U+rWsfvg+kfsFs+LV1hVOo8fIGxT5cc4/ufB7nnteRnckWCZh0hilt16HDl7r6PYdq2N2WKol/gj0Jgm93E7DybTEzuOQGMQlbg5jLhsI9gwj22lv2kIv7WGvUL9JwIDAQAB";
    public static String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIiJURcoVgX7/Q4Axy8dOJAEYOjuZGJp9ikzTAqmagVQezdT6tax++D6R+wWz4tXWFU6jx8gbFPlxzj+58Huee15GdyRYJmHSGKW3XocOXuvo9h2rY3ZYqiX+CPQmCb3cTsPJtMTO45AYxCVuDmMuGwj2DCPbaW/aQi/tYa9Qv0nAgMBAAECgYAS2aJwaAk4WkndnQ3i203r1C7s+byyOZ7aoVJmRgNtKGdahCNv8LKtpOA5IA0XV7/CRItt6LQMATU4ETk99QawJn+fWQgig5gWUlSVXJ6kIyrkAC75msr/N13sSQrIhvhbPVIyy4eFJx9dnpmcLebqtjALRnRtriU2zPNURdQSgQJBANL/EvD/puD2NfrPbszUl6uSi5R5/+INaEVUDMUHYwEb01VQqGLXCSZ5tLr7LVpXYH3GfqUzrxKutzjQZNdG6EkCQQClqIro0ydIigcxlsRt1n8DaoFPN5QHDBV54VO8XrK1zKDU2DNaxHjqtAhjs3ZeM/wc0vSA+5nMj6ACMdR0WRnvAkEAmA9qGDM2kkP+rQ3XfVjtIaJQY6X0ynX9IpGrTke/iyoBiSfC4tC0cH0fye2BRu1Xxe1KkkaIMr+lnhVZx2f7uQJARDKSnS7waSiJtZx/2UgImftqqJYU1qHe5YIh2taRc85+F6G54pAnx1e+bG0hOb7k92jRR5hMxK4MDVs+P3e++QJALQ5ZPsEGDf3GATD9df5vxNEC8Z75rrs9u2CXwDZ5w1mWabTvTKjp73LCdeYIQx/MU6NiTvT2yD8gxTxllIrt0g==";
    //私钥


    public static void main(String[] args) throws NoSuchAlgorithmException ,Exception{
//       genKeyPair();
//        String temp =RSAEncryptUtil.encrypt("123");
//       System.out.println(temp);
      System.out.println(decrypt("ONrlFDMXhusdEy44uD5GB8BQwv2kxkt4m0IKU8by6jIQNezDg7NsEm/Xk/uHJJeznELB2g/j3YsxlAX/9uK3t2nSUnQtIxkQRx4zZ2s6ZS8LVc6jhiIrGS1BVNKWxKjrN3B2hckx8RjxuUTfeJh6hW7rVCzmPjV5tBDw/gcIezw="));


    }

    /**
     * 生成随机秘钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        //初始化秘钥对生成器，秘钥大小为96-1024位
        keyPairGenerator.initialize(1024,new SecureRandom());
        //生成一个秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)  keyPair.getPublic();
        String publicKeyString = new String(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        String privateKeyString =new String(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        System.out.println(publicKeyString);
        System.out.println(privateKeyString);

    }

    /**
     * RSA公钥加密
     */
    public static String encrypt(String str) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(PUBLICKEY);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
        return outStr;
    }

    /**
     * 私钥解密字符串
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("utf-8"));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(PRIVATEKEY);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        byte[] outByte = cipher.doFinal(inputByte);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
