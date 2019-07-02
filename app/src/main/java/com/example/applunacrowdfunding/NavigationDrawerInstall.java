package com.example.applunacrowdfunding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NavigationDrawerInstall {
    Activity aa;

    public void crearHamburguesita(Activity a) {


        this.aa = a;
        SharedPreferences sp = a.getSharedPreferences("info", Context.MODE_PRIVATE);
        String txt = sp.getString("nickLogueado", "sinnick");
        String txt2 = sp.getString("correoLogueado", "sincorreo");
        String nueva = "http://192.168.1.9/phpLuna/imgUsus/" + txt + ".jpg";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toolbar toolbar = a.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.rgb(255, 152, 0));
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(a).withHeaderBackground(R.drawable.navnew)
                .addProfiles(
                        new ProfileDrawerItem().withName(txt).withEmail(txt2).withIcon(nueva)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        Intent intento = new Intent(aa, verPerfil.class);
                        intento.putExtra("nick", profile.getName().toString());
                        aa.startActivity(intento);
                        return false;
                    }

                })
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Listar Propuestas").withIcon(FontAwesome.Icon.faw_list);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Cerrar Sesión").withIcon(FontAwesome.Icon.faw_sign_out_alt);
        Drawer result = new DrawerBuilder()
                .withActivity(a)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                )
                .withSelectedItem(-1)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
                            case 1:
                                Intent intento = new Intent(aa, listarProp.class);
                                aa.startActivity(intento);
                                break;
                            case 3:
                                SharedPreferences prefs = aa.getSharedPreferences("info", aa.getApplicationContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove("correoLogueado");
                                editor.remove("mantieneAct");
                                editor.commit();
                                Intent intento2 = new Intent(aa, iniciarSesion.class);
                                aa.startActivity(intento2);
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }
}
