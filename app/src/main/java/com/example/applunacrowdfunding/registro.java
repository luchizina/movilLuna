package com.example.applunacrowdfunding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class registro extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_NICK = "nick";
    private static final String KEY_CONT = "cont";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APE = "ape";
    private static final String KEY_CORREO= "correo";
    private static final String KEY_CEL = "cel";
    private static final String KEY_CI = "ci";
    private static final String KEY_EMPTY = "";
    private EditText etNick;
    private EditText etCont;
    private EditText etNombre;
    private EditText etApe;
    private EditText etCorreo;
    private EditText etCel;
    private EditText etCI;
    private String nick;
    private String cont;
    private String nombre;
    private String ape;
    private String correo;
    private String cel;
    private String ci;
    private ProgressDialog pDialog;
    private String register_url = "http://192.168.20.135/phpLuna/usuario/nuevoUsuCel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etNick= findViewById(R.id.nick);
        etCont= findViewById(R.id.cont);
        etNombre= findViewById(R.id.nom);
        etApe= findViewById(R.id.ape);
        etCorreo= findViewById(R.id.correo);
        etCel= findViewById(R.id.cel);
        etCI= findViewById(R.id.ci);
        Button registro = findViewById(R.id.registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                nick = etNick.getText().toString().toLowerCase().trim();
                cont = etCont.getText().toString().trim();
                nombre = etNombre.getText().toString().trim();
                ape = etApe.getText().toString().trim();
                correo = etCorreo.getText().toString().trim();
                cel = etCel.getText().toString().trim();
                ci = etCI.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(nick)) {
            etNick.setError("Este campo no puede estar vacio");
            etNick.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(nombre)) {
            etNombre.setError("Este campo no puede estar vacio");
            etNombre.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(ape)) {
            etApe.setError("Este campo no puede estar vacio");
            etApe.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(cont)) {
            etCont.setError("Este campo no puede estar vacio");
            etCont.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(correo)) {
            etCorreo.setError("Este campo no puede estar vacio");
            etCorreo.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(ci)) {
            etCI.setError("Este campo no puede estar vacio");
            etCI.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(cel)) {
            etCel.setError("Este campo no puede estar vacio");
            etCel.requestFocus();
            return false;
        }
        return true;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(registro.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

        private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_NICK, nick);
            request.put(KEY_CONT, cont);
            request.put(KEY_NOMBRE, nombre);
            request.put(KEY_APE, ape);
            request.put(KEY_CORREO, correo);
            request.put(KEY_CEL, cel);
            request.put(KEY_CI, ci);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                //session.loginUser(username,fullName);
                                //loadDashboard();

                          //  }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                //etUsername.setError("Username already taken!");
                              //  etUsername.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}
