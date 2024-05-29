package com.myrsoft.apilapuntainmobiliaria.ui.inmuebles;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentInmueblesBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import java.util.List;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel vm;
    private Inmueble propiedad;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicializar(root);
        return root;
    }
    private void inicializar(View root){
        Bundle bundle = getArguments();
        vm = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        vm.getInmuebleMutable().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                propiedad = inmueble;
                binding.tvId.setText(inmueble.getId()+"");
                binding.tvDireccion.setText(inmueble.getDireccion());
                binding.tvTipo.setText(inmueble.getTipo()+"");
                binding.tvUso.setText(inmueble.getUso()+"");
                binding.tvAmbientes.setText(inmueble.getAmbientes() + "");
                binding.tvPrecio.setText("$"+inmueble.getPrecio() + "");
                binding.cbEstado.setChecked(inmueble.isEstado());
                binding.cbEstado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        propiedad.setEstado(binding.cbEstado.isChecked());
                        vm.editarDisponibilidad(propiedad);
                    }
                });
                String imagen = inmueble.getImagen().replace("\\", "/");
                String url = ApiClientRetrofit.URLBASE + imagen;
                Glide.with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivImagenInmueble);
            }
        });

        vm.cargarInmueble(getArguments());

    }
}

