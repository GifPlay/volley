package com.example.volley;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallePersonal extends AppCompatActivity {
    /* Información para el ViewHolder*/
    TextView tvNombre;
    TextView tvTelefono;
    TextView tvStatus;
    TextView tvPuesto;
    TextView tvIdPersonal;
    ImageView ivFotografia;
    TextView tvNombreImage;
    TextView tvScanText;
    RecyclerView recyclerView;
    RequestQueue queue;
    //String url = "https://raw.githubusercontent.com/GifPlay/ServerJSON/main/cupones.json";
    String url = "http://192.168.0.101/eTextil/index.php/webService/wsCupones/";
    String urlPost2 = "http://192.168.0.101/eTextil/index.php/webService/saveCupon?";
    String urlPost = "http://192.168.0.101/eTextil/index.php/webService/saveCupon?Cupon=1996-15-27-150-4&Empleado=2";

    /* Información para la peticion de cupones Volley*/
    ArrayList<Cupon> ListaCupon;
    CuponAdapter cuponAdapter;
    String rOrden;
    String rBulto;
    String rTalla;
    String rOperacion;
    String rCosto;
    String rCantidad;

    String idPersonal;
    String datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registro de cupones");

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
        //System.out.println(bundle.getString("IdPersonal"));
        Glide.with(ivFotografia).load(bundle.getString("Fotografia")).into(ivFotografia);


        /* Mandaras a llamar una funcion */
        /* Volley*/
        queue= Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.idRecyclerCodigos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        idPersonal = bundle.getString("IdPersonal");

        ListaCupon = new ArrayList<>();
        getDataCupones(idPersonal);

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
                datos = result.getContents();
                tvScanText.setText("Código: "+ datos);
                setDataCupones(idPersonal, datos);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setDataCupones(String IdPersonal, String cupon) {
       // http://192.168.0.101/eTextil/index.php/webService/saveCupon?Cupon=1996-15-27-150-4&Empleado=2
        urlPost2 = urlPost2+"Cupon="+cupon+"&Empleado="+IdPersonal;
        System.out.println(urlPost2);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, urlPost2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   String txt = response.getString("result");

                    Toast.makeText(DetallePersonal.this, txt, Toast.LENGTH_LONG).show();
                   ListaCupon.clear();
                    getDataCupones(IdPersonal);
                    datos.isEmpty();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePersonal.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        /* No ejecuta nada despues de este código*/
        queue.add(jsonObjectRequest);
    }

    /* Aqui colocaras la funciona del Volley para la consulta de los cupones */
    /* Trabajando */
    /* Gracias yo del pasado jajaja xd*/

    private void getDataCupones(String idPersonal){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url+idPersonal,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("cupones");
                    int size = jsonArray.length();

                    for (int i=0; i<size; i++){

                        JSONObject arrayCupon = (JSONObject) jsonArray.get(i);
                        rOrden = arrayCupon.get("Corte").toString();
                        rBulto = arrayCupon.get("Bulto").toString();
                        rTalla = arrayCupon.get("Talla").toString();
                        rOperacion = arrayCupon.get("Operacion").toString();
                        rCosto = arrayCupon.get("Costo").toString();
                        rCantidad = arrayCupon.get("CantidadBulto").toString();

                        ListaCupon.add(new Cupon(rOrden, rBulto, rTalla, rOperacion, rCosto, rCantidad));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /* Termina el response*/

                cuponAdapter = new CuponAdapter(ListaCupon);
                recyclerView.setAdapter(cuponAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePersonal.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        /* No ejecuta nada despues de este código*/
        queue.add(jsonObjectRequest);
    }


}
