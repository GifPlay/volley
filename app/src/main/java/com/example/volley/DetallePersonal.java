package com.example.volley;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DetallePersonal extends AppCompatActivity {
    TextView tvNombre;
    TextView tvTelefono;
    TextView tvStatus;
    TextView tvPuesto;
    TextView tvIdPersonal;
    ImageView ivFotografia;
    TextView tvNombreImage;
    TextView tvScanText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable activeDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_check_circle_24);
        Drawable inactiveDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_warning_24);

        setContentView(R.layout.activity_detalle_personal);
        tvNombre = (TextView) findViewById(R.id.idNombre);
        tvTelefono = (TextView) findViewById(R.id.idTelefono);
        tvStatus = (TextView) findViewById(R.id.idStatus);
        tvPuesto = (TextView) findViewById(R.id.idPuesto);
        tvNombreImage = (TextView) findViewById(R.id.idNameImage);
        tvIdPersonal = (TextView) findViewById(R.id.idPersonal);
        ivFotografia = (ImageView) findViewById(R.id.idFotografia);
        tvScanText = (TextView) findViewById(R.id.idScanText);

        Bundle bundle = this.getIntent().getExtras();

        tvNombre.setText(bundle.getString("Nombre"));
        tvTelefono.setText(bundle.getString("Telefono"));
        tvStatus.setText(bundle.getString("Status"));
        tvPuesto.setText(bundle.getString("Puesto"));
        tvNombreImage.setText(bundle.getString("Fotografia"));
        tvIdPersonal.setText(bundle.getString("IdPersonal"));

        /* Coloca un icono dependiendo el status del usuario*/
        if (bundle.getString("Status").equals("Activo")){
            tvStatus.setCompoundDrawablesWithIntrinsicBounds( null, null,activeDrawable,null);
        }else{
            tvStatus.setCompoundDrawablesWithIntrinsicBounds( null, null,inactiveDrawable,null);
        }
        System.out.println(bundle.getString("IdPersonal"));
        Glide.with(ivFotografia).load(bundle.getString("Fotografia")).into(ivFotografia);
    }

    //Metodo on click
    public void openScan(View view) {
        if (view.getId() == R.id.btnEscanear){
           IntentIntegrator integrator = new IntentIntegrator(this);
           integrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_128);
           integrator.setPrompt("Escanea el código de barras");
           integrator.setBeepEnabled(true);
           integrator.initiateScan();
        }
    }
    //Llamar metodo result Scan

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Llamado de información
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //Obtener información en String
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }else{
                String datos = result.getContents();
                tvScanText.setText("Código: "+ datos);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
