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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tr.com.berkaykaanedikli.sinavhazirlik11_son.databinding.ActivityGirisBinding;
import tr.com.berkaykaanedikli.sinavhazirlik11_son.databinding.ActivityUrunBinding;

public class UrunActivity extends AppCompatActivity {
    ActivityUrunBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUrunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void UrunEkle_Kayit(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String textUrunAdi = binding.editTextUrunAdi.getText().toString().trim();
        String textFiyat = binding.editTextUrunFiyat.getText().toString();
        String textAdet = binding.editTextUrunAdet.getText().toString();

        if (!textUrunAdi.isEmpty() && !textFiyat.isEmpty() && !textAdet.isEmpty()) {

            double decFiyat = Double.parseDouble(textFiyat);
            int intAdet = Integer.parseInt(textAdet);

            Map<String, Object> urun = new HashMap<>();
            urun.put("urunAdi", Objects.requireNonNull(textUrunAdi));
            urun.put("fiyat", Objects.requireNonNull(decFiyat));
            urun.put("adet", Objects.requireNonNull(intAdet));

            db.collection("urunler")
                    .add(urun).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(UrunActivity.this, "Ürün Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UrunActivity.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                                    binding.textViewDurum.setTextColor(Color.parseColor("#ff0000"));
                                    binding.textViewDurum.setText("Ürünler eklenirken bir hata oluştu");

                        }
                    });
        } else {
            binding.textViewDurum.setTextColor(Color.parseColor("#ff2400"));
            binding.textViewDurum.setText("Lütfen Boş Alanları Doldurunuz");
        }

    }
}