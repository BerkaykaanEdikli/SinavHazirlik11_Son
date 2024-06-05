package tr.com.berkaykaanedikli.sinavhazirlik11_son;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.berkaykaanedikli.sinavhazirlik11_son.databinding.ActivityGirisBinding;
import tr.com.berkaykaanedikli.sinavhazirlik11_son.databinding.ActivityKullaniciKayitBinding;

public class KullaniciKayitActivity extends AppCompatActivity {
    ActivityKullaniciKayitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKullaniciKayitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void kullaniciKayit(View view) {
        String txtEmail = binding.editTextKayitEmail.getText().toString();
        String txtPassword = binding.editTextKayitPassword.getText().toString();
        String txtRepassword = binding.editTextKayitRepassword.getText().toString();
        
        if (!txtEmail.isEmpty()){
            if (!txtPassword.isEmpty()){
                if (!txtRepassword.isEmpty()){
                    if (txtPassword.equals(txtRepassword)){
                        FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(txtEmail,txtPassword)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(KullaniciKayitActivity.this, "Kullanıcı Başarıyla Oluşturuldu", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(KullaniciKayitActivity.this, "Kullancı OLUŞTURULAMADI!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }else Toast.makeText(this, "Girdiğiniz Parolalar Uyuşmuyor", Toast.LENGTH_LONG).show();
                }else Toast.makeText(this, "Parola Tekrarı boş bırakılamaz!", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Parola boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "E-Posta boş bırakılamaz!", Toast.LENGTH_SHORT).show();
    }

    public void GirisYap(View view) {
        finish();
        startActivity(new Intent(KullaniciKayitActivity.this, GirisActivity.class));
    }
}