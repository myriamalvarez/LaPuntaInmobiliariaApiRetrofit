package com.myrsoft.apilapuntainmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentInmueblesBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel vm;
    private FragmentInmueblesBinding binding;
    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InmueblesViewModel vm = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.fbNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_inmuebles_to_nuevoInmuebleFragment);
            }
        });
        vm.getListaMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> listaActual) {
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                binding.rvInmuebles.setLayoutManager(glm);
                InmuebleAdapter iad = new InmuebleAdapter(listaActual, getContext(), getLayoutInflater());
                binding.rvInmuebles.setAdapter(iad);
            }
        });
        vm.leerInmuebles();
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);
        // TODO: Use the ViewModel
    }
}