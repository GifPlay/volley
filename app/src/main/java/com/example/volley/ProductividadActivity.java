package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Objects;

public class ProductividadActivity extends AppCompatActivity {

    TextView tvIdPersonal;
    String url ="http://172.22.16.4/etextil/index.php/webService/wsProductividad?Empleado=";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        setContentView(R.layout.activity_productividad);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Productividad semanal");

        tvIdPersonal = findViewById(R.id.idPersonal);
      tvIdPersonal.setText(bundle.getString("Nombre"));
      String idPersonal = bundle.getString("IdPersonal");



        WebView webView = (WebView) findViewById(R.id.grafica_personal);


        WebSettings webSettings = webView.getSettings(); webSettings.setJavaScriptEnabled(true); webSettings.setDomStorageEnabled(true); webSettings.setLoadWithOverviewMode(true); webSettings.setUseWideViewPort(true); webSettings.setBuiltInZoomControls(true); webSettings.setDisplayZoomControls(false); webSettings.setSupportZoom(true); webSettings.setDefaultTextEncodingName("utf-8");

        webView.loadUrl(url+idPersonal);

    }
}