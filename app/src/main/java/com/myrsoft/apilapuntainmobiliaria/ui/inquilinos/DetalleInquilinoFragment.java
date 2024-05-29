package com.myrsoft.apilapuntainmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        vm.getInquilinoMutable().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.etInquilinoId.setText(String.valueOf(inquilino.getId()+""));
                binding.etInquilinoNombre.setText(inquilino.getNombre());
                binding.etInquilinoApellido.setText(inquilino.getApellido());
                binding.etInquilinoDni.setText(String.valueOf(inquilino.getDni()+""));
                binding.etInquilinoTelefono.setText(inquilino.getTelefono());
                binding.etInquilinoEmail.setText(inquilino.getEmail());
                binding.etInquilinoGarante.setText(inquilino.getNombreGarante());
                binding.etInquilinoGaranteTelefono.setText(inquilino.getTelefonoGarante());
            }
        });
        vm.cargarInquilino(getArguments());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}