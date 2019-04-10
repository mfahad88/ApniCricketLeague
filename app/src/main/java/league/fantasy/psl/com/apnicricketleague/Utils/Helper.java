package league.fantasy.psl.com.apnicricketleague.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import league.fantasy.psl.com.apnicricketleague.model.request.TestBeanRequest;

public class Helper {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVMxpYZO46njcjm/iizphuiSJlL5P2kj16WJRT\n" +
            "OmD+rJ/DG6IsOqhEZWHOu2SUpGp+OFbNzYdRGkuDl7oWoe95v5QOMA7+8qBgwCr1/OZWp+aHkxxM\n" +
            "/to87hLEmFzWgiC8zzyHvWDzjvNJEGfPa9J0RDYjxEES7kuyhRY4KLxDowIDAQAB";
    public static void showAlertNetural(Context ctx, String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
        builder.setTitle(title).setMessage(message);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public static boolean validateEmail(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @SuppressLint("NewApi")
    public static TestBeanRequest encrypt(String messsage){
        String encryptedSecretKey = null;
        TestBeanRequest request =new TestBeanRequest();
        try{

//            Base64.Encoder encoder = Base64.getEncoder();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
            SecretKey secretKey = keyGenerator.generateKey();

            System.out.println("SecretKey: "+ Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));


            //System.out.println("Beta:: "+beta(a));
            // 2. get string which needs to be encrypted
            String text = messsage;

            // 3. encrypt string using secret key
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            /*StringBuffer cipherTextString = new StringBuffer();
            cipherTextString.append(Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))),Base64.DEFAULT));*/
            String cipherTextString =Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))),Base64.DEFAULT) ;
            System.out.println("cipherTextString: [Text with secret key encryption ]"+cipherTextString);
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(publicKeyString,Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicSpec);
            byte[] a= alpha(Base64.encodeToString(secretKey.getEncoded(),Base64.DEFAULT));

          //  System.out.println("Alpha:: "+a);
            // 6. encrypt secret key using public key
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedSecretKey =Base64.encodeToString(cipher2.doFinal(secretKey.getEncoded()),Base64.DEFAULT);
            System.out.println("encryptedSecretKey: "+encryptedSecretKey);
            request.setRequest(cipherTextString);
            request.setKey(encryptedSecretKey);
            System.out.println(request.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    public static byte[] alpha(String value) {
        System.out.println("Alphaaaaaaaaaaaaaaaa");
        String dd=value.substring(0, 3);
        String ee=value.substring(value.length()-3, value.length());
        String rem=value.substring(3,value.length()-3);
        //System.out.println(dd);
        //System.out.println(ee);
        //System.out.println(rem);
        rem=ee+rem+dd;
        System.out.println("Alphaaa:"+rem);

        return rem.getBytes();

    }
}
