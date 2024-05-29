package com.myrsoft.apilapuntainmobiliaria;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.myrsoft.apilapuntainmobiliaria.databinding.ActivityMenuBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private MenuActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmuebles, R.id.nav_inquilinos, R.id.nav_contratos, R.id.nav_salir)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MenuActivityViewModel.class);
        vm.getPropietarioMutable().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                View header = navigationView.getHeaderView(0);
                ImageView avatar = header.findViewById(R.id.ivNavAvatar);
                TextView nombreProp = header.findViewById(R.id.tvNavNombre);
                TextView emailProp = header.findViewById(R.id.tvNavEmail);

                nombreProp.setText(propietario.getNombre() + " " + propietario.getApellido());
                emailProp.setText(propietario.getEmail());
                String imagen = propietario.getAvatar().replace("\\", "/");
                String url = ApiClientRetrofit.URLBASE + imagen;
                Glide.with(getApplicationContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(avatar);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        vm.LeerUsuario();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}