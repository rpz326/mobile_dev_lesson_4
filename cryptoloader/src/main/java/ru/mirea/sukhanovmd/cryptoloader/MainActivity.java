package ru.mirea.sukhanovmd.cryptoloader;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final int LoaderID = 1234;
    private EditText editText;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void onClickButton(View view) {
        String inputText = editText.getText().toString();
        if (!inputText.isEmpty()) {
            try {
                secretKey = generateKey();
                byte[] encryptedText = encryptMsg(inputText, secretKey);
                Bundle bundle = new Bundle();
                bundle.putByteArray(MyLoader.ARG_WORD, encryptedText);
                bundle.putByteArray("key", secretKey.getEncoded());
                LoaderManager.getInstance(this).restartLoader(LoaderID, bundle, this);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка шифрования", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Текст не введен", Toast.LENGTH_SHORT).show();
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @NonNull Bundle args) {
        if (id == LoaderID) {
            return new MyLoader(this, args);
        }
        throw new IllegalArgumentException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String decryptedText) {
        Toast.makeText(this, "Расшифрованная фраза: " + decryptedText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed("any data used as random seed".getBytes());
        javax.crypto.KeyGenerator kg = javax.crypto.KeyGenerator.getInstance("AES");
        kg.init(256, sr);
        return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
    }

    public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes());
    }
}
