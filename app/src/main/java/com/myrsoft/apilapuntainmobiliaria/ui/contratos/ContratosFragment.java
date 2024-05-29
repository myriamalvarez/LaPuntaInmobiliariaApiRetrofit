package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentContratosBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding binding;
    private ContratosViewModel vm;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ContratosViewModel vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getListaMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                RecyclerView rv = root.findViewById(R.id.rvContratos);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                ContratoAdapter adapter = new ContratoAdapter(inmuebles, getContext(), getLayoutInflater());
                rv.setAdapter(adapter);
            }
        });
        vm.leerInmuebles();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        // TODO: Use the ViewModel
    }

}