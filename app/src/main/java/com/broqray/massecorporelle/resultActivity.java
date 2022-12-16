package com.broqray.massecorporelle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class resultActivity extends AppCompatActivity {

    private TextView resultValue;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultValue = findViewById(R.id.result_valeur_imc);
        resultText = findViewById(R.id.result_categories_imc);

        Intent resultActivity = getIntent();

        String valeurIMC = String.valueOf(resultActivity.getDoubleExtra("valeurIMC", -1));
        resultValue.setText(valeurIMC);
        resultText.setText(resultActivity.getStringExtra("resultIMC"));
    }
}
