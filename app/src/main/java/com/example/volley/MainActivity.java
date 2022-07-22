package com.example.volley;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Personal> ListaPersonal;
    ArrayList<Personal> BusquedaEmpleado;
    RecyclerView recyclerView;
    SearchView txtBuscar;
    PersonalAdapter personalAdapter;
    PersonalAdapter EmpleadoAdapter;

    RequestQueue queue;
    //String url="https://raw.githubusercontent.com/GifPlay/ServerJSON/main/personal.json";
    String url="http://172.22.16.4/etextil/index.php/webService/wsPersonal";
    String urlEmpleado="http://172.22.16.4/etextil/index.php/WebService/wsEmpleado?Empleado=";
    String rNombre;
    String rTelefono;
    String rStatus;
    String rFotografia;
    String rPuesto;
    String rIdPersonal;


    String idPersonal;
    String datos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Volley*/
        queue= Volley.newRequestQueue(this);
        /* Busqueda de datos */
        txtBuscar = findViewById(R.id.idBusqueda);

        ListaPersonal = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.idRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        txtBuscar.setOnQueryTextListener(this);

        getData();



    }

    private void getData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("personal");
                    //System.out.println(jsonArray);
                    int size = jsonArray.length();
                    //System.out.println(size);

                    for (int i=0; i<size; i++){
                        //String txt = jsonArray.get(i).toString();
                        JSONObject array = (JSONObject) jsonArray.get(i);

                        rNombre = array.get("Nombre").toString();
                        rTelefono = array.get("Telefono").toString();
                        rStatus = array.get("Status").toString();
                        rPuesto = array.get("Puesto").toString();
                        //rIdPersonal = array.get("IdPersonal").toString();
                        rFotografia = array.get("Fotografia").toString();
                        rIdPersonal = array.get("IdPersonal").toString();

                        //System.out.println(rNombre + rTelefono + rStatus + rPuesto +rFotografia);
                        ListaPersonal.add(new Personal(rNombre, rTelefono, rStatus, rPuesto, rFotografia, rIdPersonal));
                    }

                } catch (JSONException e) {
                   // e.printStackTrace();
                    FancyToast.makeText(MainActivity.this, e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                /* Termina el response*/
                personalAdapter = new PersonalAdapter(ListaPersonal);
                recyclerView.setAdapter(personalAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(MainActivity.this, "Surgio un problema al comunicarse con el servidor", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        /* No ejecuta nada despues de este código*/
        queue.add(jsonObjectRequest);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String s) {
        personalAdapter.filtrado(s);
        return false;
    }

    //Metodo on click para abrir el scaner
    public void openScan(View view) {
        if (view.getId() == R.id.btnEscanear) {
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
        if (result != null) {
            if (result.getContents() == null) {
                //  Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
                //MotionToast
                FancyToast.makeText(this, "¡Escaneo cancelado!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
            } else {
                datos = result.getContents();
                getEmpleado(datos);
                //tvScanText.setText("Código: " + datos);
                //setDataCupones(idPersonal, datos);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getEmpleado(String bIdPersonal){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, urlEmpleado+bIdPersonal,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("personal");
                    //System.out.println(jsonArray);
                    int size = jsonArray.length();
                    //System.out.println(size);

                    for (int i=0; i<size; i++){
                        //String txt = jsonArray.get(i).toString();
                        JSONObject array = (JSONObject) jsonArray.get(i);

                        rNombre = array.get("Nombre").toString();
                        rTelefono = array.get("Telefono").toString();
                        rStatus = array.get("Status").toString();
                        rPuesto = array.get("Puesto").toString();
                        //rIdPersonal = array.get("IdPersonal").toString();
                        rFotografia = array.get("Fotografia").toString();
                        rIdPersonal = array.get("IdPersonal").toString();

                        //System.out.println(rNombre + rTelefono + rStatus + rPuesto +rFotografia);
                        ListaPersonal.add(0,new Personal(rNombre, rTelefono, rStatus, rPuesto, rFotografia, rIdPersonal));
                    }

                } catch (JSONException e) {
                    // e.printStackTrace();
                    FancyToast.makeText(MainActivity.this, e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

                /* Termina el response*/

               personalAdapter = new PersonalAdapter(ListaPersonal);
                //EmpleadoAdapter = new PersonalAdapter(BusquedaEmpleado);
               // recyclerView.setAdapter(EmpleadoAdapter);
                recyclerView.setAdapter(personalAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(MainActivity.this, error.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        /* No ejecuta nada despues de este código*/
        queue.add(jsonObjectRequest);
    }



}