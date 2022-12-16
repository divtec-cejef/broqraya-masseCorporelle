package com.broqray.massecorporelle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

import kotlin.text.UStringsKt;

public class MainActivity extends AppCompatActivity {

    private EditText ET_saisie_taille;
    private EditText ET_saisie_poids;
    private Button BT_calculer;
    private Button BT_annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_saisie_taille = findViewById(R.id.main_taille_et);
        ET_saisie_poids = findViewById(R.id.main_poids_et);
        BT_calculer = findViewById(R.id.main_bt_calculer);
        BT_annuler = findViewById(R.id.main_bt_annuler);

        BT_calculer.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BT_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });

        ET_saisie_taille.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_calculer.setEnabled(!ET_saisie_taille.getText().toString().equals("") &&
                        !ET_saisie_poids.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ET_saisie_poids.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_calculer.setEnabled(!ET_saisie_taille.getText().toString().isEmpty() &&
                        !ET_saisie_poids.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        BT_calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultActivity = new android.content.Intent(MainActivity.this, resultActivity.class);

                final float taille = Integer.parseInt(ET_saisie_taille.getText().toString());
                final float poids = Integer.parseInt(ET_saisie_poids.getText().toString());
                final BigDecimal imc = calculerIMC(taille, poids);

                resultActivity.putExtra("valeurIMC", imc.doubleValue());
                resultActivity.putExtra("resultIMC", "Vous êtes " + getStringIMC(imc));

                startActivity(resultActivity);
            }
        });
    }

    private void resetFields() {
        ET_saisie_taille.setText("");
        ET_saisie_poids.setText("");
        BT_calculer.setEnabled(false);
        ET_saisie_taille.requestFocus();
    }

    private BigDecimal calculerIMC(float taille, float poids) {
        if(taille > 3) {
            taille /= 100;
        }
        return new BigDecimal(poids / Math.pow(taille, 2));
    }

    private String getStringIMC(BigDecimal imc) {
        if (imc.doubleValue() < 18.5) {
            return getText(R.string.result_text_sous_poids).toString();
        } else if (imc.doubleValue() < 24.9) {
            return getText(R.string.result_text_imc_normal).toString();
        } else if (imc.doubleValue() < 29.9) {
            return getText(R.string.result_text_imc_en_surpoids).toString();
        } else if (imc.doubleValue() < 34.9) {
            return getText(R.string.result_text_imc_obese).toString();
        } else {
            return getText(R.string.result_text_imc_obesite_severe).toString();
        }
    }
}