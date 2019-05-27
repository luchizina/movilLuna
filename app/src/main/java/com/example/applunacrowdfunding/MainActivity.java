package com.example.applunacrowdfunding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
Button registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationDrawerInstall nav = new NavigationDrawerInstall();
        nav.crearHamburguesita(this);
//codigo mio
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            // drop inactive
            try {
                if (!networkInterface.isUp())
                    continue;
            } catch (SocketException e) {
                e.printStackTrace();
            }

            // smth we can explore
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if(!addr.isLinkLocalAddress() && addr instanceof Inet4Address && !addr.isLoopbackAddress() && !networkInterface.getName().equals("eth0")){
                    System.out.println(String.format(addr.getHostAddress()));
                    TextView a = findViewById(R.id.textView8);
                    a.setText(String.format(addr.getHostAddress()));
                }

            }
        }
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado= sp.getString("correoLogueado","sinusuario");

        String mantieneAct = sp.getString("mantieneAct","algo");
        if((!emailLogueado.equals("sinusuario"))){
        View btnCerrar =  findViewById(R.id.btnCerrar);
        btnCerrar.setVisibility(View.VISIBLE);
            View btnRegistrar = findViewById(R.id.buttonR);
            btnCerrar.setVisibility(View.GONE);
        }
        //hasta aca



    }
    public void traerProp(View vista){
        String prop = "hola";
        Intent intento = new Intent(MainActivity.this,traerProp.class);
        intento.putExtra("prop",prop);
        startActivity(intento);

    }

    public void iniciarS(View vista){

        String algo ="oh";
        Intent intento = new Intent(MainActivity.this,iniciarSesion.class);
        intento.putExtra("algo",algo);
        startActivity(intento);
    }

    public void listarProp(View vista){
        Intent intento = new Intent(MainActivity.this,listarProp.class);
        startActivity(intento);
    }

    public void cerrarSesion(View vista){
        SharedPreferences prefs =  getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("correoLogueado");
        editor.remove("mantieneAct");
        editor.commit();
        Intent intento = new Intent(MainActivity.this,iniciarSesion.class);
        startActivity(intento);
    }


    public void registrarUsu(View vista){
        Intent i = new Intent(MainActivity.this, registro.class);
        startActivity(i);
    }



}