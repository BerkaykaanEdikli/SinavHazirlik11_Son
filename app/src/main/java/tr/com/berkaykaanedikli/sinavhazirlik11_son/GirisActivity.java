package tr.com.berkaykaanedikli.sinavhazirlik11_son;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.berkaykaanedikli.sinavhazirlik11_son.databinding.ActivityGirisBinding;

public class GirisActivity extends AppCompatActivity {
    ActivityGirisBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGirisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void GirisYap(View view) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String textEposta = binding.editTextGirisEposta.getText().toString().trim();
        String textParola = binding.editTextGirisParola.getText().toString();

        if (!textEposta.isEmpty()) {
            if (!textParola.isEmpty()) {

                firebaseAuth
                        .signInWithEmailAndPassword(textEposta, textParola)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(GirisActivity.this, "Başarı ile giriş yaptınız.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(GirisActivity.this, MainActivity.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                binding.textView.setTextColor(Color.parseColor("@color/red"));
                                binding.textView.setText("Giriş başarısız\n" + e.getLocalizedMessage());

                            }
                        });


            } else binding.textView.setText("Şifre boş olamaz");
        } else binding.textView.setText("E-posta boş olamaz");
    }

    public void kullaniciKayit(View view) {
        finish();
        startActivity(new Intent(GirisActivity.this, KullaniciKayitActivity.class));
    }

    public void SifreSifirlama(View view) {
        String eposta = binding.editTextGirisEposta.getText().toString().trim();
        if (eposta.isEmpty()) {
            binding.textView.setTextColor(Color.parseColor("#ff0800"));
            binding.textView.setText("E-posta boş bırakılamaz");

        }
        else
        {
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(eposta)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            binding.textView.setTextColor(Color.parseColor("#66cd00"));
                            binding.textView.setText("Parola Sıfırlama E-postası gönderildi");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            binding.textView.setTextColor(Color.parseColor("#ff0800"));
                            binding.textView.setText("Parola Sıfırlama e-postası gönderilemedi\n" + e.getLocalizedMessage());

                        }
                    });
        }
    }
}