package com.example.applunacrowdfunding;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    Button registro;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overrideDrawerImageLoaderPicasso();
        setContentView(R.layout.activity_main);
        NavigationDrawerInstall nav = new NavigationDrawerInstall();
        nav.crearHamburguesita(this);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ERROR", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("SUCCESSTOKEN", msg);

                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

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
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (!addr.isLinkLocalAddress() && addr instanceof Inet4Address && !addr.isLoopbackAddress() && !networkInterface.getName().equals("eth0")) {
                    System.out.println(String.format(addr.getHostAddress()));
                    TextView a = findViewById(R.id.textView8);
                    a.setText(String.format(addr.getHostAddress()));
                }

            }
        }
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");

        String mantieneAct = sp.getString("mantieneAct", "algo");
        if ((!emailLogueado.equals("sinusuario"))) {


        }
        //hasta aca
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


    public void iniciarS(View vista) {

        String algo = "oh";
        Intent intento = new Intent(MainActivity.this, iniciarSesion.class);
        intento.putExtra("algo", algo);
        startActivity(intento);
    }

    public void listarProp(View vista) {
        Intent intento = new Intent(MainActivity.this, listarProp.class);
        startActivity(intento);
    }

    public void cerrarSesion(View vista) {
        SharedPreferences prefs = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("correoLogueado");
        editor.remove("mantieneAct");
        editor.commit();
        Intent intento = new Intent(MainActivity.this, iniciarSesion.class);
        startActivity(intento);
    }


    public void registrarUsu(View vista) {
        Intent i = new Intent(MainActivity.this, registro.class);
        startActivity(i);
    }

    private void overrideDrawerImageLoaderPicasso(){
        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.get().cancelRequest(imageView);
            }

           /*
           @Override
           public Drawable placeholder(Context ctx) {
               return super.placeholder(ctx);
           }

           @Override
           public Drawable placeholder(Context ctx, String tag) {
               return super.placeholder(ctx, tag);
           }
           */
        });
    }


}