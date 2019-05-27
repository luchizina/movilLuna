package com.example.applunacrowdfunding;

import android.app.Activity;
import android.view.View;

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

public class NavigationDrawerInstall {
    public void crearHamburguesita(Activity a)
    {


        Toolbar toolbar = a.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(a.getResources().getColor(R.color.colorAccent));
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(a)
                .withHeaderBackground(R.color.md_white_1000)
                .addProfiles(
                        new ProfileDrawerItem().withName("Prueba").withEmail("prueba@gmail.com").withIcon(FontAwesome.Icon.faw_user1)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
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
                        return true;
                    }
                })
                .build();
    }
}
