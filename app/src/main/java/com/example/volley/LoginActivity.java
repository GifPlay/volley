package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsuario;
    EditText edtPassword;
    RequestQueue queue;

    String rNombre;
    String rIdPersonal;

    //String urlLogin="http://192.168.0.101/eTextil/index.php/webService/wsIngresar";
    String urlLogin="http://172.22.16.4/etextil/index.php/webService/wsIngresar";
    String usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Inicio de sesión");
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        queue = Volley.newRequestQueue(this);

        recuperarPreferencias();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = edtUsuario.getText().toString();
                password=edtPassword.getText().toString();
                if (!usuario.isEmpty()&& !password.isEmpty()){

                    validateUser(urlLogin);

                }else{
                    FancyToast.makeText(getApplicationContext(), "Información no valida", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                }


            }
        });

    }

    private void validateUser(String url){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guardarPreferencias();
                    /* Se convierte a Objeto Json*/
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println(jsonObject);


                    try {
                        assert jsonObject != null;
                        JSONObject jsonUsuario =  jsonObject.getJSONObject("usuario");

                         rIdPersonal = jsonUsuario.get("IdPersonal").toString();
                         rNombre = jsonUsuario.get("Nombre").toString();


                        System.out.println(rIdPersonal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IdPersonal", rIdPersonal);
                    bundle.putString("Nombre", rNombre);

                    FancyToast.makeText(getApplicationContext(), "Bienvenido a e-Textil" , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                }else{
                    FancyToast.makeText(getApplicationContext(), "Usuario o contraseña invalidos" , FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(LoginActivity.this, error.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UsuarioPersonal", edtUsuario.getText().toString());
                parametros.put("PasswordPersonal", edtPassword.getText().toString());
               // System.out.println(parametros);
                return parametros;
            }
        };
        queue.add(stringRequest);
    }

        private void validarUser(String url){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                    //System.out.println(queue);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("UsuarioPersonal", edtUsuario.getText().toString());
                    parametros.put("PasswordPersonal", edtPassword.getText().toString());

                    return parametros;
                }
            };
            queue.add(jsonObjectRequest);
        }

        private void guardarPreferencias(){
            SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usuario", usuario);
            editor.putString("password", password);
            editor.commit();
        }

        private void recuperarPreferencias(){
            SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
            edtUsuario.setText(preferences.getString("usuario", ""));
            edtPassword.setText(preferences.getString("password", ""));

        }

}