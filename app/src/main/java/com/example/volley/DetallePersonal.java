package com.example.volley;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;

public class DetallePersonal extends AppCompatActivity {
    TextView tvNombre;
    TextView tvTelefono;
    TextView tvStatus;
    TextView tvPuesto;
    TextView tvIdPersonal;
    ImageView ivFotografia;
    TextView tvNombreImage;


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
}
